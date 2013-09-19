#!/bin/sh

BASE_PATH=${0%/*}
LIB_PATH=${BASE_PATH}/lib

#create class path
for dirname in `ls -F |grep -e "^paperworker.*/"`
do
	PACKAGES_PATH=${PACKAGES_PATH}${dirname}bin:
done

#run
java \
-cp \
.:\
${PACKAGES_PATH}\
${LIB_PATH}/h2-1.3.173.jar \
paperworker.core.ui.command.PaperWorker

