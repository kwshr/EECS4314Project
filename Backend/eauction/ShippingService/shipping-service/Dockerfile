FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/shipping-service-0.0.1-SNAPSHOT.jar /app/shipping-service-0.0.1-SNAPSHOT.jar

EXPOSE 8082

CMD ["java", "-jar", "shipping-service-0.0.1-SNAPSHOT.jar"]
