#!/usr/bin/env bash
mvn -Dmaven.test.skip=true -U clean package

docker build -t registry.cn-beijing.aliyuncs.com/zhongxb-docker-repository/springcloud-order .

#docker login --username=zhongxuebin2011 registry.cn-beijing.aliyuncs.com

docker push registry.cn-beijing.aliyuncs.com/zhongxb-docker-repository/springcloud-order