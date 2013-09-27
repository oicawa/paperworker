#!/bin/sh

BASE_PATH=${0%/*}
LIB_PATH=${BASE_PATH}/lib

#create class path
for dirname in `ls -F |grep -e "^pw.*/"`
do
	PACKAGES_PATH=${PACKAGES_PATH}${dirname}bin:
done

#run
java \
-cp \
.:\
${PACKAGES_PATH}\
${LIB_PATH}/h2-1.3.173.jar \
pw.core.ui.command.PaperWorker

