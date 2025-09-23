package fitnesstracker.repositories;

import fitnesstracker.models.Developer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeveloperRepository extends CrudRepository<Developer, Long> {

    Optional<Developer> findByEmail(String email);
}
