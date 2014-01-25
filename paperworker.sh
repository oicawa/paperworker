#!/bin/sh

UI=$1

cd ./pw.ui.${UI}

BASE_PATH=`pwd`
LIB_PATH=../lib

#create class path
for dirname in `ls -F ..|grep -e "^pw.*/"`
do
	PACKAGES_PATH=${PACKAGES_PATH}../${dirname}bin:
done
PACKAGES_PATH=${PACKAGES_PATH}../nenga/bin:

#run
java \
-cp \
..:\
${PACKAGES_PATH}\
${LIB_PATH}/h2-1.3.173.jar \
pw.ui.${UI}.PaperWorker

