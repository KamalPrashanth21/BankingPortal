# This is a Temporary container
# Used to build the jar file

FROM maven:3.9.6-eclipse-temurin-21-jammy AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

#Runtime environment, where the new container is spawned
#It contains the compiled jar file from build stage and the JRE to run the jar file
FROM eclipse-temurin:21-jre-jammy

ARG JAR_FILE=target/*.jar

COPY --from=build /app/${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]