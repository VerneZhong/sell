**mac安装docker:**
# 安装命令
brew cask install docker

# docker -v / version
# docker info

#网易的镜像地址：http://hub-mirror.c.163.com

#Docker安装 rabbitmq方式：
docker run -d --hostname zxb-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3.7.18-management