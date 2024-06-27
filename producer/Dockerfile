FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/producer-0.0.1-SNAPSHOT.jar producer.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "producer.jar"]