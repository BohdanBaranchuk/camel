# First stage: build the application
FROM maven:3.8.3-amazoncorretto-17 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

# Second stage: create a slim image
FROM amazoncorretto:17-alpine-jdk
LABEL maintainer="Bohdan Baranchuk baranchuk.b@gmail.com"
LABEL version="1.0"
LABEL description="Camel micro application"
COPY --from=build /app/target/camelapp-*.jar /app.jar
HEALTHCHECK --interval=5s \
            --timeout=3s \
            CMD curl -f http://localhost:8080/products/test || exit 1
ENTRYPOINT ["java", "-jar", "/app.jar"]
