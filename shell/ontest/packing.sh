#!/bin/bash
source /etc/profile
SELF=$(cd $(dirname $0); pwd -P)/$(basename $0)
SELF_DIR=`dirname $SELF`

#project no
APP_NAME="demoproject"

#build file name
BUILD_FILE_NAME="${APP_NAME}-`date +%Y%m%d-%H%M`.tar.gz"


#clean old build
echo "==============================***clean old build***======================================="
[[ ! -d  /data/release/${APP_NAME}/dist  ]] && mkdir -p /data/release/${APP_NAME}/dist ;

if [ -d  /data/release/${APP_NAME}/work ] ; then 
   rm -rf /data/release/${APP_NAME}/work/*
fi
#create app dir
mkdir -p /data/release/${APP_NAME}/work/config/
mkdir -p /data/release/${APP_NAME}/work/web/
mkdir -p /data/release/${APP_NAME}/work/lib/
mkdir -p /data/release/${APP_NAME}/work/htdocs/
mkdir -p /data/release/${APP_NAME}/work/shell/
mkdir -p /data/release/${APP_NAME}/work/init/

#build
echo "==============================*** build project ***======================================="
cd /data/release/${APP_NAME}/src/
svn up
mvn -Dmaven.test.skip=true -am clean package

#copy
echo "==============================***cp to /data/release/${APP_NAME}/work ***======================================"
#copy *.war *.jar and config
find . -maxdepth 4 -regex ".*/target/[^\/]*.war"  -exec cp -f {} /data/release/${APP_NAME}/work/web/ \;
find . -maxdepth 4 -regex ".*/target/[^\/]*.jar"  -exec cp -f {} /data/release/${APP_NAME}/work/lib/ \;
cp -Rf  config/* /data/release/${APP_NAME}/work/config/

#copy http static elements
if [ -d "/data/release/${APP_NAME}/src/src/main/webapp/" ] ; then
    cp -Rf /data/release/${APP_NAME}/src/src/main/webapp/* /data/release/${APP_NAME}/work/htdocs/
fi
for pmodule in `ls  /data/release/${APP_NAME}/src/` ; do
   if [ -d "/data/release/${APP_NAME}/src/${pmodule}/src/main/webapp/" ] ; then
      mkdir -p /data/release/${APP_NAME}/work/htdocs/${pmodule}/
	  cp -Rf /data/release/${APP_NAME}/src/${pmodule}/src/main/webapp/* /data/release/${APP_NAME}/work/htdocs/${pmodule}/
   fi
done
#del WEB-INF *.jsp
find /data/release/${APP_NAME}/work/htdocs/ -type "d"  -name "WEB-INF" | xargs rm -rf
find /data/release/${APP_NAME}/work/htdocs/ -type "f"  -name "*.jsp" | xargs rm -rf

#copy shell
cp -Rf /data/release/${APP_NAME}/src/shell/* /data/release/${APP_NAME}/work/shell/
rm -f /data/release/${APP_NAME}/work/shell/packing.sh
find /data/release/${APP_NAME}/work/shell/ -name "*\.sh"  -exec chmod +x {} \;

#copy init data
if [ -d "/data/release/${APP_NAME}/src/doc/initdata/" ] ; then
  cp -Rf /data/release/${APP_NAME}/src/doc/initdata/* /data/release/${APP_NAME}/work/init/
fi

#del .svn
find /data/release/${APP_NAME}/work/ -type "d"  -name "*.svn" | xargs rm -rf


#tar
chown :${APP_NAME} /data/release/${APP_NAME}/* -R
chmod 755 -R /data/release/${APP_NAME}/work/
cd /data/release/${APP_NAME}/work/
tar -zcf ${BUILD_FILE_NAME} *
mv -i ${BUILD_FILE_NAME} /data/release/${APP_NAME}/dist/
cp -f /data/release/${APP_NAME}/dist/${BUILD_FILE_NAME} /data/release/${APP_NAME}/dist/${APP_NAME}.tar.gz
echo "==============================***  Packing Done ***======================================="

