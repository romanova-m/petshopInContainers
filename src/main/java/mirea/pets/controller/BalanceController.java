package mirea.pets.controller;

import mirea.pets.service.BalanceService;
import mirea.pets.domain.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = "balance", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Balance> balance(){
        return balanceService.balance();
    }
}
