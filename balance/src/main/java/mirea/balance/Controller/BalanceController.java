package mirea.balance.Controller;

import mirea.balance.Service.BalanceService;
import mirea.balance.Domain.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = "balance", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Balance> balance(){
        return balanceService.balance();
    }

    @RequestMapping(value = "balance/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Balance> balanceId(@PathVariable long id){
        return balanceService.balanceId(id);
    }

    @RequestMapping(value = "/balance", method = RequestMethod.PUT)
    @ResponseBody
    public long putBal(@RequestBody Balance newBalance) {
        return balanceService.updateBalance(newBalance);
    }

    @RequestMapping(value = "/balance/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public long deleteBal(@PathVariable long id) {
        return balanceService.deleteBalance(id);
    }
}
