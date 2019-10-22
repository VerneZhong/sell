**mac安装docker:**
# 安装命令
brew cask install docker

# docker -v / version
# docker info

#网易的镜像地址：http://hub-mirror.c.163.com

#Docker运行 rabbitmq方式：
docker run -d --hostname zxb-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3.7.18-management

# Docker运行 Redis
docker run -d -p 6379:6379 redis:5.0.6

# Docker运行 zipkin
docker run -d -p 9411:9411 openzipkin/zipkin

# Docker 运行 eureka
docker run -p 8761:8761 -d hub.c.163.com/springcloud/eureka

# CentOS docker 镜像加速
vim /etc/docker/daemon.json
{
  "registry-mirrors": ["http://hub-mirror.c.163.com"]
}
# 刷新配置
systemctl daemon-reload
# 重启docker
systemctl restart docker