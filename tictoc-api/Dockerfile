FROM openjdk:21
WORKDIR /app

ARG PROFILE=prod
ENV PROFILE=${PROFILE}

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]