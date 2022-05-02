FROM openjdk:17-alpine
VOLUME /tmp
COPY target/articles*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]