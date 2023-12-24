# Stage 1: Build the application
FROM maven:3.6.3-openjdk-11 AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean install

# Stage 2: Create a minimal runtime image
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar
EXPOSE 8080
CMD ["java", "-jar", "demo.jar"]
