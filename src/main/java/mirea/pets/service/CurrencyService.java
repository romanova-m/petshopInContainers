package mirea.pets.service;

import mirea.pets.domain.Currency;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class CurrencyService {
    private HashMap<Long,Currency> currency = new HashMap<>();

    @PostConstruct
    public void init(){
        currency.put(Long.valueOf(1) ,new Currency(1,1, 1.5));
    }

    public HashMap<Long, Currency> currency() {
        return currency;
    }
}
