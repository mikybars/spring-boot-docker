FROM openjdk:8-jre-alpine
COPY build/dependencies /app/dependencies
COPY build/resources/main /app/resources
COPY build/classes/java/main /app/classes
ENTRYPOINT ["java", "-cp", "/app/dependencies/*:/app/resources:/app/classes", "com.mperezi.springtasksapp.SpringTasksApplication"]
