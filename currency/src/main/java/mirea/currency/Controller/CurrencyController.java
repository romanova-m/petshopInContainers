package mirea.currency.Controller;

import mirea.currency.Domain.Currency;
import mirea.currency.Service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(value = "currency", method = RequestMethod.GET)

    @ResponseBody
    public Iterable<Currency> currency(){
        return currencyService.currencies();
    }
}
