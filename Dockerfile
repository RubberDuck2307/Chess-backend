FROM gradle:latest AS BUILD

WORKDIR /usr/app/

COPY . .

RUN gradle build -x test

FROM openjdk:21-jdk

WORKDIR /usr/app/
COPY --from=BUILD /usr/app/ .
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=k8s", "-jar", "/usr/app/build/libs/chess-1.0-SNAPSHOT.jar"]