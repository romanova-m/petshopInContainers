FROM java:8
RUN mkdir /app
WORKDIR /app
COPY ./target/balance-1.0.jar .
EXPOSE 8081:8081
ENTRYPOINT ["java", "-jar", "balance-1.0.jar"]