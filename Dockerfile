FROM openjdk:17-jdk-alpine
LABEL authors="sadvik gowda"

EXPOSE 8090

COPY target/user-service-0.0.1.jar user-service-0.0.1.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/user-service-0.0.1.jar"]
