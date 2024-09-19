FROM maven:3-eclipse-temurin-22-alpine AS build

ARG SKIP_TESTS=false
ARG SKIP_ANALYSIS=false
ARG SONAR_QUBE_PROJECT_KEY
ARG SONAR_QUBE_URL
ARG SONAR_QUBE_TOKEN

COPY . /usr/src/
WORKDIR /usr/src/

RUN mvn clean package -DskipTests=${SKIP_TESTS}
RUN if [ "${SKIP_ANALYSIS}" = "false" ]; then \
      mvn sonar:sonar \
      -Dsonar.projectKey=${SONAR_QUBE_PROJECT_KEY} \
      -Dsonar.host.url=${SONAR_QUBE_URL} \
      -Dsonar.login=${SONAR_QUBE_TOKEN}; \
    fi

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