#!/usr/bin/env bash

#项目路径
export SHELL_PATH=$(cd $(dirname "$0");pwd)
export PROJECT_PATH=$(cd $SHELL_PATH;cd ..;pwd)
source ${SHELL_PATH}/env.sh

${SHELL_PATH}/stop.sh
${SHELL_PATH}/start.sh
