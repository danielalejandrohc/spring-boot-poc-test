FROM openjdk:8-jdk-alpine
RUN rm -r /tmp
VOLUME /tmp
ARG JAR_FILE
RUN rm app.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
