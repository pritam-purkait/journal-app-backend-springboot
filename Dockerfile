# ── builder stage ─────────────────────────────────────
FROM maven:3.8.7-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests         \
    && echo ">> built:" target/*.jar

# ── runtime stage ─────────────────────────────────────
FROM openjdk:17-jdk-slim
WORKDIR /app
ARG JAR_NAME=*.jar
# copy the jar from the builder stage into /app/app.jar
COPY --from=builder /app/target/${JAR_NAME} app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]