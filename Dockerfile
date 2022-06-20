FROM openjdk:17-alpine
LABEL maintainer="wiktorkielar@gmail.com"
ADD target/employees-0.0.1-SNAPSHOT.jar employees.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "employees.jar"]