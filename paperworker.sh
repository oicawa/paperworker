#!/bin/sh

UI=$1

cd ./pw.ui.${UI}

BASE_PATH=`pwd`
LIB_PATH=~/workspace/lib

#create class path
for dirname in `ls -F ..|grep -e "^pw.*/"`
do
	PACKAGES_PATH=${PACKAGES_PATH}../${dirname}bin:
done

#create lib path
#for libname in `ls ../../lib`
for libname in `ls ~/workspace/lib`
do
	PACKAGES_PATH=${PACKAGES_PATH}~/workspace/lib/${libname}:
done
PACKAGES_PATH=${PACKAGES_PATH}../nenga/bin:

echo ${PACKAGES_PATH}

#run
java \
-cp \
..:\
${PACKAGES_PATH} \
pw.ui.${UI}.PaperWorker

