# Use Java 21 JDK as the base image
FROM eclipse-temurin:21-jdk-jammy

# Copy the built JAR file into the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","/app.jar"]
