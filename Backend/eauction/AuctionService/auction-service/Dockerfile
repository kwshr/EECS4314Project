FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/auction-service-0.0.1-SNAPSHOT.jar /app/auction-service-0.0.1-SNAPSHOT.jar

EXPOSE 8082

CMD ["java", "-jar", "auction-service-0.0.1-SNAPSHOT.jar"]
