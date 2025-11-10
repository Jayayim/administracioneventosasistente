FROM eclipse-temurin:23-jdk-alpine
LABEL authors="PeinSoR"
VOLUME /temp
COPY target/AdministracionEventos-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","app.jar"]
EXPOSE 8080