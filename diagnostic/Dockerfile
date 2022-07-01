FROM java:8
VOLUME /tmp
COPY ./target/diagnostic-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]