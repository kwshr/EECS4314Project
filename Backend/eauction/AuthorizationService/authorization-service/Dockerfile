FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/authorization-service-0.0.1-SNAPSHOT.jar /app/authorization-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "authorization-service-0.0.1-SNAPSHOT.jar"]
