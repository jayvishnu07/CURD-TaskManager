
# Stage 1: Build the Spring Boot application
FROM adoptopenjdk/openjdk11:ubi as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests

# Stage 2: Create a minimal docker image with the built JAR
FROM adoptopenjdk/openjdk11:ubi

ARG JAR_FILE=backend/target/crudApplication-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} crudApplication-0.0.1-SNAPSHOT.jar

EXPOSE 8443

ENTRYPOINT ["java","-jar","crudApplication-0.0.1-SNAPSHOT.jar"]
