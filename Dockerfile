# Use Java 21 JDK as the base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the project source code
COPY . .

# Build the JAR inside the container
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","target/server-0.0.1-SNAPSHOT.jar"]
