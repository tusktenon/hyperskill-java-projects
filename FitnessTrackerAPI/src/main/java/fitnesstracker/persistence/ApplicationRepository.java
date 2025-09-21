package fitnesstracker.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

    List<Application> findAllByCategory(Application.Category category);

    Optional<Application> findByApiKey(UUID apiKey);
}
