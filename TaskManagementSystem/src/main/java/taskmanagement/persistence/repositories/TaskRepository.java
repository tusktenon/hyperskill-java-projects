package taskmanagement.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import taskmanagement.business.entities.Account;
import taskmanagement.business.entities.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByOrderByCreatedAtDesc();

    List<Task> findByAuthorOrderByCreatedAtDesc(Account author);

    List<Task> findByAssigneeOrderByCreatedAtDesc(Account assignee);

    List<Task> findByAuthorAndAssigneeOrderByCreatedAtDesc(Account author, Account assignee);
}
