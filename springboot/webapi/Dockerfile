FROM openjdk:20 AS build
WORKDIR /app
COPY . .
RUN ./mvnw package -DskipTests

# Use a base image with JRE
FROM openjdk:20
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]