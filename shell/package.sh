#!/usr/bin/env bash

#项目路径
export SHELL_PATH=$(cd $(dirname "$0");pwd)
export PROJECT_PATH=$(cd $SHELL_PATH;cd ..;pwd)
source ${SHELL_PATH}/env.sh

cd $PROJECT_PATH
mvn package -DskipTests=true

