# STAGE 1: Build the application
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /usr/src/app

COPY pom.xml .
RUN mvn verify --fail-never

COPY . .
#RUN mvn clean package

# STAGE 2: Setup the API server image
FROM openjdk:17-jdk-oracle

WORKDIR /app

COPY --from=builder /usr/src/app/target/*.jar app.jar

# Set the default active Spring profile
ENV SPRING_PROFILES_ACTIVE=local

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8080"]