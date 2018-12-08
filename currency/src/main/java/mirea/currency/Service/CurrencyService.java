package mirea.currency.Service;

import mirea.currency.Domain.Currency;
import mirea.currency.Repository.CurrencyRepository;
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
