# For Java 11
#FROM adoptopenjdk/openjdk11:latest
FROM openjdk:17-oracle

# Refer to Maven build -> finalName
ARG JAR_FILE=target/replicated-log-master.jar

# cd /app
WORKDIR /app

# cp target/replicated-log-master.jar /app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]