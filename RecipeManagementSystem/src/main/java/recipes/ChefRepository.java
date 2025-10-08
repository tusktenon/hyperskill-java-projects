package recipes;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChefRepository extends CrudRepository<Chef, Long> {

    boolean existsByEmail(String email);

    Optional<Chef> findByEmail(String email);
}
