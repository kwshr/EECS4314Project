FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/seller-service-0.0.1-SNAPSHOT.jar /app/seller-service-0.0.1-SNAPSHOT.jar

EXPOSE 8081

CMD ["java", "-jar", "seller-service-0.0.1-SNAPSHOT.jar"]
