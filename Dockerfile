FROM openjdk:12-jdk

WORKDIR /app

COPY target/*.jar /app/api.jar

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]
