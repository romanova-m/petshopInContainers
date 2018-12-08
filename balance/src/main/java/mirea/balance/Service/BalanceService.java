package mirea.balance.Service;

import mirea.balance.Domain.Balance;
import mirea.balance.Repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class BalanceService {
    @Autowired
    BalanceRepository balanceRepository;

    @PostConstruct
    public void init() {
        balanceRepository.save(new Balance(1, 100));
    }

    public Iterable<Balance> balance() {
        return balanceRepository.findAll();
    }

    public void setBalance(long user_id, int val) {
        for (Balance balance : balanceRepository.findAll()) {
            if (balance.getUser_id() == user_id) balance.setVal(val);
        }
    }

    public long getBalance(long user_id) {
        for (Balance balance : balanceRepository.findAll()) {
            if (balance.getUser_id() == user_id) return balance.getVal();
        }
        return 0;
    }

    public long updateBalance(Balance balance) {
        balanceRepository.save(balance);
        return getBalance(balance.getUser_id());
    }

    public long deleteBalance(long id) {
        balanceRepository.deleteById(id);
        return id;
    }

    public Optional<Balance> balanceId(long id) {
        return balanceRepository.findById(id);
    }
}