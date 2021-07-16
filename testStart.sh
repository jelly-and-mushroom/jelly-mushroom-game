#!/bin/bash
psCount=`ps -ef|grep 'full-moon-0.0.1-SNAPSHOT.jar' | wc -l`
if [ "$#" -gt 2 ];then
	echo "已有至少两个服务启动"
	exit
fi
rm -fr full-moon-0.0.1-SNAPSHOT.jar
cp /Users/reimuwang/IdeaProjects/project2/jelly-mushroom-game/target/full-moon-0.0.1-SNAPSHOT.jar ./full-moon-0.0.1-SNAPSHOT.jar
java -Djava.awt.headless=false -jar full-moon-0.0.1-SNAPSHOT.jar