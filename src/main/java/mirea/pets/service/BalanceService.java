package mirea.pets.service;

import mirea.pets.domain.Balance;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;

@Component
public class BalanceService {
    private HashMap<Long, Balance> map = new HashMap<>();

    @PostConstruct
    public void init(){
        map.put(Long.valueOf(1), new Balance(1,1,100));

    }

    public Collection<Balance> balance() {
        return map.values();
    }

    public void setBalance(long user_id, int val) {
        for (Balance balance: map.values()){
            if (balance.getUser_id() == user_id) balance.setVal(val);
        }
    }

    public int getBalance(long user_id) {
        for (Balance balance: map.values()){
            if (balance.getUser_id() == user_id) return balance.getVal();
        }
        return 0;
    }
}
