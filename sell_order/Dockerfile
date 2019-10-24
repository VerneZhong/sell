# 构建eureka应用到docker 容器
# docker build -t springcloud2/eureka .
FROM hub.c.163.com/library/java:latest

ADD order_server/target/*.jar app.jar

MAINTAINER zhongxb zhongxuebin171980062@gmail.com

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]