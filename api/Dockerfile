#FROM gradle:8.1-jdk17-alpine AS build
#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
#
#COPY . .
#RUN gradle clean jar

#FROM eclipse-temurin:17-jdk-alpine
#ENV APP_HOME=/usr/app
#
#WORKDIR $APP_HOME
#ARG JAR_FILE=$APP_HOME/build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#
#EXPOSE 5000
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM eclipse-temurin:17-jdk-alpine

ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 5000
ENTRYPOINT ["java","-jar","/app.jar"]
