# 构建eureka应用到docker 容器
# docker build -t springcloud2/eureka .
FROM hub.c.163.com/library/java:latest

ADD user_server/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]