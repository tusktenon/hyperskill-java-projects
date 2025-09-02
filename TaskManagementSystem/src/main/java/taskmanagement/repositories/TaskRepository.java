package taskmanagement.repositories;

import org.springframework.data.repository.CrudRepository;
import taskmanagement.models.Account;
import taskmanagement.models.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByOrderByCreatedAtDesc();

    List<Task> findByAuthorOrderByCreatedAtDesc(Account author);
}
