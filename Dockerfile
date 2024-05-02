FROM openjdk:17-jdk-alpine
MAINTAINER danielopara
WORKDIR /urs/app
COPY ./target/restaurant-app-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","restaurant-app-0.0.1-SNAPSHOT.jar"]
