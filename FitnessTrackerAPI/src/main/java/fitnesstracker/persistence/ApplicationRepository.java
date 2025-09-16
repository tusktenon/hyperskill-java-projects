package fitnesstracker.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Optional<Application> findByApiKey(UUID apiKey);
}
