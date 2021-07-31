FROM node:14.7-buster AS nodebuild

WORKDIR /Portfolio-Frontend

COPY Portfolio-Frontend/Dependencies.txt /Portfolio-Frontend
COPY Portfolio-Frontend/package.json /Portfolio-Frontend
COPY Portfolio-Frontend/package-lock.json /Portfolio-Frontend
COPY Portfolio-Frontend/public/ /Portfolio-Frontend/public
COPY Portfolio-Frontend/src/ /Portfolio-Frontend/src
COPY Portfolio-Frontend/tsconfig.json /Portfolio-Frontend

RUN ["npm", "install"]
RUN ["npm", "run-script", "build"]


FROM maven:3.8.1-jdk-8 AS build

WORKDIR /Backend
COPY Forge-Backend/src/ /Backend/src
COPY Forge-Backend/pom.xml /Backend/pom.xml
COPY --from=nodebuild /Portfolio-Frontend/build /Backend/src/main/resources/static

RUN ["mvn", "clean", "package"]


FROM openjdk:8-jdk-buster
COPY --from=build /Backend/target/demo-0.0.1-SNAPSHOT.war demo.war

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=production","/demo.war"]
