FROM openjdk:22-jdk
WORKDIR /app
COPY target/spring-boot-banking.jar /app/app.jar
COPY src/main/resources/application.properties /app/config/application.properties
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.config.location=/app/config/application.properties"]
