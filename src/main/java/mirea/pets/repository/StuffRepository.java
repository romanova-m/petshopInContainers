package mirea.pets.repository;

import mirea.pets.domain.Stuff;
import org.springframework.data.repository.CrudRepository;

public interface StuffRepository extends CrudRepository<Stuff, Long> {
}
