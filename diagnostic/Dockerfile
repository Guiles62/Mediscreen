FROM java:8
VOLUME /tmp
COPY ./build/libs/diagnostic.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]