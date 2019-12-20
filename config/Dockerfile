FROM java:8
RUN mkdir /app
WORKDIR /app
COPY ./target/config-1.0.jar .
EXPOSE 8083:8083
ENTRYPOINT ["java", "-jar", "config-1.0.jar"]