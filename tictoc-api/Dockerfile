FROM openjdk:21
WORKDIR /app

COPY ./build/libs/*.jar app.jar

ARG PROFILE=prod
ENV PROFILE=${PROFILE}

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]