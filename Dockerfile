# syntax=docker/dockerfile:1

# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn .mvn
RUN ./mvnw -q -e -DskipTests dependency:go-offline || mvn -q -e -DskipTests dependency:go-offline
COPY src src
RUN ./mvnw -q -DskipTests package || mvn -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV JAVA_OPTS=""
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]