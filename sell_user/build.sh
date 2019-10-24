#!/usr/bin/env bash
mvn -Dmaven.test.skip -U clean package

docker build -t registry.cn-beijing.aliyuncs.com/zhongxb-docker-repository/springcloud-user .

docker push registry.cn-beijing.aliyuncs.com/zhongxb-docker-repository/springcloud-user

