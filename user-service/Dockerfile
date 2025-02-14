# Official OpenJDK 21 JRE image as base image
FROM openjdk:21-jre-slim

# Author and Description Labels for the image
LABEL authors="Arman Iqbal"
LABEL description="This is a Dockerfile for the User Service"

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY build/libs/user-service-0.0.1-SNAPSHOT.jar user-service.jar

# Execute the JAR file
CMD ["java", "-jar", "user-service.jar"]