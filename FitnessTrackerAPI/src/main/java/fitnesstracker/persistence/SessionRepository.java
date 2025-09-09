package fitnesstracker.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {

    List<Session> findAllByOrderByUploadedAtDesc();
}
