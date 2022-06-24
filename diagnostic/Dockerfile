FROM java:8
VOLUME /tmp
COPY ./target/diagnostic.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]