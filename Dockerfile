FROM openjdk:17-jdk-alpine
MAINTAINER danielopara
WORKDIR /urs/app
COPY ./target/restaurant-app-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","restaurant-app-0.0.1-SNAPSHOT.jar"]


#FROM openjdk:17-jdk-slim
#
#WORKDIR /urs/app
#COPY . .
#RUN mvn clean install
#
#CMD mvn spring-boot:run