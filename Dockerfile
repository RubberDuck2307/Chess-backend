FROM gradle:latest AS BUILD

WORKDIR /usr/app/

COPY . .

RUN gradle build -x test

FROM openjdk:21-jdk

ENV JAR_NAME=chess-1.0-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
EXPOSE 8080
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME