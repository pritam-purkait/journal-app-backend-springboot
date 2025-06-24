FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# default command; no hard-coded URI here
ENTRYPOINT ["java","-jar","/app.jar"]
