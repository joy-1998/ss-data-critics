FROM azul/zulu-openjdk-alpine:17-jre

WORKDIR /app

COPY target/*.jar critics_service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "critics_service.jar"]