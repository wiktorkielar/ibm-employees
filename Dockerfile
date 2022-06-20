FROM openjdk:17
LABEL maintainer="wiktorkielar@gmail.com"
ADD target/employees-*.jar employees.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "employees.jar"]