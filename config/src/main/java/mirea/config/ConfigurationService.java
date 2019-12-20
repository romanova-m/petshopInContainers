package ru.mirea.config;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigurationService {

        private Map<String, String> configs = new HashMap<>();

        @PostConstruct
        public void init() {
            configs.put("auth", "8080");
            configs.put("users", "8080");
            configs.put("balance", "8081");
            configs.put("cart", "8082");
            configs.put("config", "8083");
            configs.put("currency", "8084");
            configs.put("gateway", "8085");
            configs.put("pet", "8086");
            configs.put("stuff", "8086");
        }

        public Map<String, String>  getAll() {
            return configs;
        }
}
