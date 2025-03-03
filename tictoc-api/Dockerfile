FROM openjdk:21
WORKDIR /app

COPY ./build/libs/*.jar app.jar
COPY ./src/main/resources/application-prod.yml /app/application.yml

ARG PROFILE=prod
ENV PROFILE=${PROFILE}

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]