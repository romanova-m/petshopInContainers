FROM java:8
RUN mkdir /app
WORKDIR /app
COPY ./target/items-1.0.jar .
EXPOSE 8086:8086
ENTRYPOINT ["java", "-jar", "items-1.0.jar"]