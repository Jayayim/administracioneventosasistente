# Etapa de build: compila el JAR con Maven y Java 17
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos los archivos necesarios
COPY pom.xml .
COPY src ./src

# Compilamos el proyecto (sin tests)
RUN mvn -B -DskipTests clean package

# Etapa de runtime: imagen ligera solo con el JAR
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiamos el JAR generado en la etapa de build
COPY --from=build /app/target/AdministracionEventos-0.0.1-SNAPSHOT.jar app.jar

# Render va a pasar PORT en una variable de entorno.
# Tu application.properties ya tiene: server.port=${PORT:8080}
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
