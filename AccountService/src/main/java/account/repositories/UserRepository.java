package account.repositories;

import account.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    List<User> findAllByOrderById();

    Optional<User> findByEmailIgnoreCase(String email);
}
