package fitnesstracker.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeveloperRepository extends CrudRepository<Developer, Long> {

    boolean existsByEmail(String email);

    Optional<Developer> findByEmail(String email);
}
