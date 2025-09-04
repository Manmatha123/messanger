# Stage 1: Build the JAR using Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy all source files
COPY . .

# Build the JAR
RUN mvn clean package -DskipTests

# Stage 2: Run the app using only JDK
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/server-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
