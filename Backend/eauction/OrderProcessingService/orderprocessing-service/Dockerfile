FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/orderprocessing-service-0.0.1-SNAPSHOT.jar /app/orderprocessing-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "orderprocessing-service-0.0.1-SNAPSHOT.jar"]
