# -- ETAPA 1: Build
FROM maven:3.9.7-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# -- ETAPA 2: Runtine
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar product.jar
ENTRYPOINT ["java", "-jar", "product.jar"]