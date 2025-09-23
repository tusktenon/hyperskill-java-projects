package fitnesstracker.repositories;

import fitnesstracker.models.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {

    List<Session> findAllByOrderByUploadedAtDesc();
}
