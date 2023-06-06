# Use a base image with JDK installed
FROM maven:3.8.4-openjdk-17-slim
# Set the working directory
WORKDIR /app
# Copy the Maven executable to the container
COPY . .
# Copy the project descriptor files
# Build the application
RUN mvn package
# Expose the port that the application will run on
EXPOSE 8080
# Set the entrypoint command to run the application
ENTRYPOINT ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]