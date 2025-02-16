FROM openjdk:21
EXPOSE 8080
COPY ./build/libs/*.jar app.jar
ARG PROFILE=prod
ENV PROFILE=${PROFILE}
COPY .env .env
ENTRYPOINT ["/bin/sh", "-c", "export $(grep -v '^#' .env | xargs) && java \
    -Dspring.profiles.active=${PROFILE} \
    -Djava.security.egd=file:/dev/./urandom \
    -jar /app.jar"]