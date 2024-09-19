FROM maven:3-eclipse-temurin-22-alpine AS build

ARG SKIP_TESTS=false

COPY . /usr/src/
WORKDIR /usr/src/

RUN mvn clean package -DskipTests=${SKIP_TESTS}

FROM eclipse-temurin:22-jdk-alpine AS extract

COPY --from=build /usr/src/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:22-jre-alpine

WORKDIR /app

COPY --from=extract dependencies/ ./
COPY --from=extract snapshot-dependencies/ ./
COPY --from=extract spring-boot-loader/ ./
COPY --from=extract application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]