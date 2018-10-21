package mirea.pets.service;

import mirea.pets.domain.Balance;
import mirea.pets.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BalanceService {
    @Autowired
    BalanceRepository balanceRepository;

    @PostConstruct
    public void init(){
        balanceRepository.save(new Balance(1,100));
    }

    public Iterable<Balance> balance() {
        return balanceRepository.findAll();
    }

    public void setBalance(long user_id, int val) {
        for (Balance balance: balanceRepository.findAll()){
            if (balance.getUser_id() == user_id) balance.setVal(val);
        }
    }

    public int getBalance(long user_id) {
        for (Balance balance: balanceRepository.findAll()){
            if (balance.getUser_id() == user_id) return balance.getVal();
        }
        return 0;
    }
}