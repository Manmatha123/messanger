# Stage 1: Build with Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy source code
COPY . .

# Build the JAR
RUN mvn clean package -DskipTests

# Stage 2: Run with JDK only
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy built JAR
COPY --from=build /app/target/server-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
