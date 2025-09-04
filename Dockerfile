# Stage 1: Build with Maven + Java 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

# Copy all source files
COPY . .

# Build the JAR
RUN mvn clean package -DskipTests

# Stage 2: Run with JDK 21
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/server-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
