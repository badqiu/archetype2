#!/bin/bash

#############################################################################################
##
##      Description: This is a project operation and maintenance update script.
## 
##############################################################################################

###
. /lib/lsb/init-functions
##
source /etc/profile
##
SELF=$(cd $(dirname $0); pwd -P)/$(basename $0)
SELF_DIR=`dirname $SELF`
#
## set args
#
rsync_server="package.yygamedev.com"
rsync_user="demoproject"
rsync_passwd="demoprojectrelease"
resin_user="www-data"
resin_group="www-data"
app_dir="/data/app"
bak_dir="/data2/backup"
project_dir="$app_dir/$APP_NAME"
bak_project_dir="$bak_dir/$APP_NAME"
bak_time=`date +%F_%H_%M`

#
## judge args is null or not 
#
if [ -z $app_dir ] || [ -z $bak_dir ];then
	echo "\$app_dir or \$bak_dir is null !"
	exit 1
fi
#
## Judge directory exists or not 
#
if [ ! -d $app_dir ];then
        mkdir -p $app_dir
fi
if [ ! -d $project_dir ];then
	mkdir -p $project_dir
fi
if [ ! -d $bak_dir ];then
	mkdir -p $bak_dir
fi
if [ ! -d $bak_project_dir ];then
	mkdir -p $bak_project_dir
fi

# arguments $projectno $dwenv appoint at /etc/profile
if [ -z ${APP_NAME} ];then
	echo "Argument \$APP_NAME is null ! Please appoint \$APP_NAME at (/etc/profile)"
	exit 1
fi
if [ -z ${DWENV} ];then
        echo "Argument \$DWENV is null ! Must be one of them in ' dev test prod '."
        exit 1
fi
DWENVARRAY=("dev" "test" "prod")
envmatch=$(echo ${DWENVARRAY[@]} | grep -o ${DWENV}) 
if [ -z  ${envmatch}  ] ;then
	echo "\$DWENV=${DWENV} is not correct ! Must be one of them in ' dev test prod '."
	exit 1
fi

## deploy 
function_deploy(){
	if [ -f "$bak_project_dir/$APP_NAME.tar.gz" ];then
		rm -rf $app_dir/$APP_NAME/*
		cd $app_dir/$APP_NAME
		tar xzf $bak_project_dir/$APP_NAME.tar.gz
	else
		echo "$bak_project/$APP_NAME.tar.gz not exists!"
		exit 1
	fi
}

## back /data/app/$APP_NAME
function_backup(){
        if [ -d $app_dir/$APP_NAME ];then
                cd $app_dir
		tar czf $APP_NAME.lastest.tar.gz $APP_NAME
                mv $app_dir/$APP_NAME.lastest.tar.gz $bak_project_dir/$APP_NAME.lastest.tar.gz
                cp $bak_project_dir/$APP_NAME.lastest.tar.gz $bak_project_dir/$bak_time.$APP_NAME.tar.gz
	else
		echo "Local directory $app_dir/$APP_NAME not exists!"
		return 1
	fi

}

## restore  /data/app/$APP_NAME lastest version
function_restore(){
	if [ -f $bak_project_dir/$APP_NAME.lastest.tar.gz ];then
        	cd $app_dir
		tar czf backup_before_restore.$APP_NAME.$bak_time.tar.gz $APP_NAME
		mv backup_before_restore.$APP_NAME.$bak_time.tar.gz $bak_project_dir
		rm -rf $APP_NAME/*
		###echo "tar xzf  $bak_project_dir/$APP_NAME.lastest.tar.gz"
		tar xzf  $bak_project_dir/$APP_NAME.lastest.tar.gz
	else
		echo "$bak_project_dir/$APP_NAME.lastest.tar.gz not exist!"
		exit 1
	fi
}


## Rsync remote server  /data/app/$APP_NAME
function_sync(){
        ## set rsync_passwd
        export RSYNC_PASSWORD=$rsync_passwd 
        # rsync project files
        rsync -vzrtopg --progress  $rsync_user@$rsync_server::$APP_NAME $bak_dir/$APP_NAME
}

case "$1" in 
        update ) 
		$SELF backup
		$SELF rsync
		log_daemon_msg "Deploy project: " "$APP_NAME"
		function_deploy  >> /dev/null
		if [ $? -eq 0 ];then
			log_end_msg 0
	                chown -R $resin_user.$resin_group $app_dir
                	log_daemon_msg "Executing: chmod +x $app_dir/$APP_NAME/shell/*.sh" ""
                	chmod +x $app_dir/$APP_NAME/shell/*.sh
                	if [ $? -eq 0 ];then
                	        log_end_msg 0
                	else
                	        log_end_msg 1
                	fi
		else
			log_end_msg 1
			log_failure_msg "Please check $APP_NAME.lastest.tar.gz is exists or not"
		fi
                ;;
	rsync)
		log_daemon_msg "Rsync remote file: $bak_project_dir/$APP_NAME.tar.gz" "rsync from $rsync_server"
		function_sync >> /dev/null
                if [ $? -eq 0 ];then
                        log_end_msg 0
                else
                        log_end_msg 1
                        log_failure_msg "Please check $rsync_server configure file !!"
                fi
                ;;
	backup )
		log_daemon_msg "Backup local directory $app_dir/$APP_NAME" ""
                function_backup >> /dev/null
                if [ $? -eq 0 ];then
                        log_end_msg 0
                else
                        log_end_msg 1
                        log_failure_msg "Please check $app_dir/$APP_NAME exists or not !"
                fi
		;;
        restore )
		log_daemon_msg "Restore local lastest version :" "$APP_NAME"
                function_restore
		if [ $? -eq 0 ];then
                        log_end_msg 0
                else
                        log_end_msg 1
                        log_failure_msg "Please check $app_dir/$APP_NAME.lastest.tar.gz exist or not !!"
                fi
                ;;
        *)
                echo "Usage: $SELF { update | rsync | backup | restore}"
esac


