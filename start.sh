#!/bin/bash
psCount=`ps -ef|grep 'full-moon-0.0.1-SNAPSHOT.jar' | wc -l`
if [ "$#" -ne 1 ];then
	echo "已有服务启动"
	exit
fi
nohup java -Djava.awt.headless=false -jar full-moon-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &