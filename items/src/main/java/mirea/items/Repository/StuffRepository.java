package mirea.items.Repository;

import mirea.items.Domain.Stuff;
import org.springframework.data.repository.CrudRepository;

public interface StuffRepository extends CrudRepository<Stuff, Long> {
}
