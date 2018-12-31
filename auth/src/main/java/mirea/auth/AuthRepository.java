package mirea.auth;

import org.springframework.data.repository.CrudRepository;

public interface AuthRepository extends CrudRepository <User, Long> {
}
