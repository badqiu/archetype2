#!/bin/bash
export PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin
PROJECT_PATH=$(cd $(dirname "$0");pwd)
PID_FILE="${PROJECT_PATH}/app.pid"

if [ $# -le 0 ];then
    echo "请输入实例名"
    exit
else
    
    cd $PROJECT_PATH
    if [ -f ${PID_FILE} ];then
        kill `cat ${PID_FILE}`
        count=0
        while true
        do
            sleep 2
            check=$(ps -ef|grep -v grep|awk -v p="$(cat ${PID_FILE})" '{if($2==p) print $0}')
            if [ -z "${check}" ];then
                rm -f ${PID_FILE}
                break
            fi
            let count++
            if [ ${count} -gt 10 ];then
                break
            fi
        done
    else
        echo "No such pid file ${PID_FILE}!"
    fi
fi
