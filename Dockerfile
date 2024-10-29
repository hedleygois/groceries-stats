#FROM openjdk:20
#EXPOSE 8080:8080
#RUN mkdir /app
##COPY ./build/libs/*-all.jar /app/groceries-stats-be.jar
#COPY ./build/libs/groceries-stats-0.0.1-SNAPSHOT.jar /app/groceries-stats.jar
#ENTRYPOINT ["java","-jar","/app/groceries-stats.jar"]
# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app
COPY . .

# Build the application
RUN gradle build --no-daemon -x test

# Stage 2: Create the runtime image
#FROM arm64v8/eclipse-temurin:17.0.9_9-jre-alpine
FROM arm64v8/eclipse-temurin:21.0.5_11-jre-alpine

WORKDIR /app

# Install required dependencies for running JVM apps on Alpine
RUN apk add --no-cache tzdata bash

# Copy the built artifact from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Configure JVM for container environment
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/urandom"

# Expose the application port
EXPOSE 8080

# Start the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]