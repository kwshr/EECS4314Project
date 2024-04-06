FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/bidding-service-0.0.1-SNAPSHOT.jar /app/bidding-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "bidding-service-0.0.1-SNAPSHOT.jar"]
