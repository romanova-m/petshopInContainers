package mirea.pets.controller;

import mirea.pets.service.CurrencyService;
import mirea.pets.domain.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(value = "currency", method = RequestMethod.GET)

    @ResponseBody
    public HashMap<Long, Currency> currency(){
        return currencyService.currency();
    }
}
