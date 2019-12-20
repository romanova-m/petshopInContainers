FROM java:8
RUN mkdir /app
WORKDIR /app
COPY ./target/cart-1.0.jar .
EXPOSE 8087:8087
ENTRYPOINT ["java", "-jar", "cart-1.0.jar"]