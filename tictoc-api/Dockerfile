FROM openjdk:21
WORKDIR /app

COPY ./build/libs/*.jar app.jar

ARG PROFILE=prod
ENV SPRING_PROFILES_ACTIVE=${PROFILE}

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar app.jar"]