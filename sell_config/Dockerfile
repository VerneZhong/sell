# 构建eureka应用到docker 容器
# docker build -t springcloud2/eureka .
FROM hub.c.163.com/library/java:latest

MAINTAINER zhongxb zhongxuebin171980062@gmail.com

ADD target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app.jar"]