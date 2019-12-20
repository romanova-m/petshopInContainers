package mirea.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CartMicroservice {
    public static void main(String[] args) {
        SpringApplication.run(CartMicroservice.class);
    }
}
