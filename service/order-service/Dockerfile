FROM openjdk:21

ARG FILE_JAR=target/*.jar

ADD ${FILE_JAR} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8082


