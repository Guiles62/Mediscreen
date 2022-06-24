FROM java:8
VOLUME /tmp
COPY ./target/patient.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]