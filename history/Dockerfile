FROM java:8
VOLUME /tmp
COPY ./target/history.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]