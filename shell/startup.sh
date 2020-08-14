#!/usr/bin/env bash

#项目路径
export PROJECT_PATH=$(cd $(dirname "$0");pwd)
source ${PROJECT_PATH}/env.sh

export VALID_USER='ubuntu'

export JAVA_HOME=/usr/local/java
export PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:${JAVA_HOME}/bin

#当前应用环境
export APP_ENV=prod
#项目名
export APP_NAME=demoproject
export LOG_ROOT="/data/log/${APP_NAME}"
export PID_FILE="${PROJECT_PATH}/app.pid"

# 只允许某用户运行
if [ $USER = $VALID_USER ];then

    cd $PROJECT_PATH
    [ -f ./var ]  &&  source  ./var
    [ -f ./spk.log.rotate ]  &&  chmod +x  ./spk.log.rotate 
   

    if [ ! -f ${PID_FILE} ];then
        libs="`ls -1 ${PROJECT_PATH}/lib/*.jar | awk '{printf ":"$1}'`"
        # 加载lib包
        export CLASSPATH=${PROJECT_PATH}/conf:$CLASSPATH:$libs

        # 需要填写启动命令和定义服务域
        
		########请按需要修改DOMIAN########
		DOMAIN="-jar lib/demoproject-admin-server-1.0-SNAPSHOT.jar"
		SERVER_CLASS="${DOMAIN}"

		########请按需要修改java启动命令#######
		${JAVA_HOME}/bin/java \
					-server \
					-Dspring.profiles.active=${APP_ENV} \
					-Dapp.name=$APP_NAME \
					-Dproject.path=$PROJECT_PATH \
					-DloggerRoot=${LOG_ROOT} \
					-Djava.net.preferIPv4Stack=true \
					-Xloggc:/dev/shm/${APP_NAME}_gc.log \
					-XX:+PrintReferenceGC \
					-XX:+ParallelRefProcEnabled \
					-Xmx${JAVA_MEMORY-2048}M \
					-Xms${JAVA_MEMORY-2048}M \
					-Xss256k \
					-XX:PermSize=128M \
					-XX:MaxPermSize=128M \
					-XX:+PrintGCDateStamps \
					-XX:+PrintGCDetails \
					-XX:+UseG1GC \
					-XX:+UnlockExperimentalVMOptions \
					-XX:G1LogLevel=finest \
					-XX:InitiatingHeapOccupancyPercent=25 \
					-XX:MaxGCPauseMillis=200 \
					-XX:G1HeapRegionSize=4m \
					-XX:+PrintFlagsFinal \
					-XX:ParallelGCThreads=12 \
					-XX:ConcGCThreads=4 \
					-XX:+PrintGCApplicationStoppedTime \
					-XX:+PrintGCApplicationConcurrentTime \
					${SERVER_CLASS} 2>&1 | spk.log.rotate ${LOG_ROOT}/server_stdout.%Y-%M-%D.log 1  &              
    
      
        sleep 2
         [  !  -f   ${PID_FILE} ]  && echo `ps -ef|grep "${DOMAIN}"|grep -v grep|awk '{print $2}'` > ${PID_FILE}
        echo "server start successfully!"
    else
        echo "${PID_FILE} is exits,please check server is running!"
    fi
else
    echo "you must start process with ${VALID_USER} user!"
fi
