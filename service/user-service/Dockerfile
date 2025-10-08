FROM openjdk:21

ARG FILE_JAR=target/*.jar

ADD ${FILE_JAR} api-user.jar

ENTRYPOINT ["java", "-jar", "/api-user.jar"]

EXPOSE 8081

