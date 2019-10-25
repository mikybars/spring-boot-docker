FROM openjdk:8-jre-alpine
COPY build/libs/spring-tasks-app-*.jar /app.jar
ENTRYPOINT java -jar /app.jar
