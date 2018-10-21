package mirea.pets.repository;

import mirea.pets.domain.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
