package taskmanagement.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import taskmanagement.business.entities.Account;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<Account> findByEmailIgnoreCase(String email);
}
