package mirea.balance.Repository;

import mirea.balance.Domain.Balance;
import org.springframework.data.repository.CrudRepository;

public interface BalanceRepository extends CrudRepository<Balance, Long> {

}
