# Use OpenJDK 11
FROM openjdk:11 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use the official OpenJDK image for runtime with JDK 11
FROM openjdk:11-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/space-ship-project.jar ./space-ship-project.jar

# Expose the port the app runs on
EXPOSE 8080

# Set the command to run the jar
ENTRYPOINT ["java", "-jar", "space-ship-project.jar"]
