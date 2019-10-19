# 构建eureka应用到docker 容器
# docker build -t springcloud2/eureka .
FROM hub.c.163.com/mymonkey110/jdk1.8:latest

ADD target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app.jar"]