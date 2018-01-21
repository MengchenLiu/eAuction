FROM openjdk:8-jdk-alpine

# Install some apks
RUN apk update
RUN apk upgrade
###### RUN apk add --update mysql-client	## Not needed in webserver
RUN apk add --no-cache bash

# Install Spring Boot artifact
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
