FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/payment-service-0.0.1-SNAPSHOT.jar /app/payment-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "payment-service-0.0.1-SNAPSHOT.jar"]
