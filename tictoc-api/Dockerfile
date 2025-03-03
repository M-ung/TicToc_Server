FROM openjdk:21
WORKDIR /app

COPY ./build/libs/*.jar app.jar

COPY ./src/main/resources/application-prod.yml /app/application.yml

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]