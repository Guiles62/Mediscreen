FROM java:8
VOLUME /tmp
COPY ./build/libs/history.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]