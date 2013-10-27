#!/bin/sh

cd ./pw.ui.command

BASE_PATH=`pwd`
LIB_PATH=../lib

#create class path
for dirname in `ls -F ..|grep -e "^pw.*/"`
do
	PACKAGES_PATH=${PACKAGES_PATH}../${dirname}bin:
done

#run
java \
-cp \
..:\
${PACKAGES_PATH}\
${LIB_PATH}/h2-1.3.173.jar \
pw.ui.command.PaperWorker

