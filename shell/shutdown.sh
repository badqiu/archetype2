#!/bin/bash
export PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin
SHELL_PATH=$(cd $(dirname "$0");pwd)
PROJECT_PATH=$(cd $SHELL_PATH;cd ..;pwd)
PID_FILE="${PROJECT_PATH}/app.pid"

DOMAIN="lib/demoproject-admin-server-1.0-SNAPSHOT.jar"

cd $PROJECT_PATH
if [ -f ${PID_FILE} ];then
	kill `cat ${PID_FILE}`
	#echo "kill -9 stop project"
	#kill -9 $(ps -ef|grep "${DOMAIN}"|gawk '$0 !~/grep/ {print $2}' |tr -s '\n' ' ')
	
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

	echo "kill end"
else
	echo "No such pid file ${PID_FILE}!"
fi

