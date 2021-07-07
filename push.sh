#!/bin/bash

if [ "$#" -ne 1 ];then
	echo "必须输入commit"
	exit
fi

git add .
git commit -m "$1"
git push