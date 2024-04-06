FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/itemcatalogue-service-0.0.1-SNAPSHOT.jar /app/itemcatalogue-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "itemcatalogue-service-0.0.1-SNAPSHOT.jar"]
