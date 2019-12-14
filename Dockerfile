FROM openjdk:8-jre-alpine
ADD build/dependencies /app/dependencies
ADD build/resources/main /app/resources
ADD build/classes/java/main /app/classes
ENTRYPOINT java -cp /app/dependencies/*:/app/resources:/app/classes com.mperezi.springtasksapp.SpringTasksApplication
