# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Build the application with Maven
RUN apt-get update && apt-get install -y maven
RUN mvn clean install

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Define environment variable
ENV NAME World

# Run the application when the container launches
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
