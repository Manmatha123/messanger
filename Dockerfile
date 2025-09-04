# Stage 1: Build
FROM eclipse-temurin:21-jdk-jammy-slim AS build

WORKDIR /app

# Copy project source
COPY . .

# Build JAR
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jdk-jammy-slim

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/server-0.0.1-SNAPSHOT.jar app.jar

# Run app
ENTRYPOINT ["java","-jar","/app/app.jar"]
