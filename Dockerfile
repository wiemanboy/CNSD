FROM maven:3-eclipse-temurin-22-alpine AS build

COPY . /usr/src/
WORKDIR /usr/src/

RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jre-alpine

WORKDIR /app
COPY --from=build /usr/src/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]