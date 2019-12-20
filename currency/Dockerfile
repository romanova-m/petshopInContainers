FROM java:8
RUN mkdir /app
WORKDIR /app
COPY ./target/currency-1.0.jar .
EXPOSE 8084:8084
ENTRYPOINT ["java", "-jar", "currency-1.0.jar"]