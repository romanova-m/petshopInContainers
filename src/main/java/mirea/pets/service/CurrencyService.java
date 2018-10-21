package mirea.pets.service;

import mirea.pets.domain.Currency;
import mirea.pets.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    @PostConstruct
    public void init(){
        currencyRepository.save(new Currency(1, 1.5));
    }

    public Iterable<Currency> currencies(){
        return currencyRepository.findAll();
    }
}
