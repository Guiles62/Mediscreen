FROM java:8
VOLUME /tmp
COPY ./target/history-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]