FROM eclipse-temurin:17.0.15_6-jdk-alpine

VOLUME /tmp

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY application.yml /config/application.yml

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=file:/config/application.yml"]