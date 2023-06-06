# Use a base image with JDK installed
FROM openjdk:17-jdk-slim
# Set the working directory
WORKDIR /app
# Copy the Maven executable to the container
COPY mvnw .
COPY .mvn .mvn
# Copy the project descriptor files
COPY pom.xml .
COPY src src
# Build the application
RUN ./mvnw package
# Expose the port that the application will run on
EXPOSE 8080
# Set the entrypoint command to run the application
ENTRYPOINT ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]