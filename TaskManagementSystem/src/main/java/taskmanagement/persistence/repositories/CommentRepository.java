package taskmanagement.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import taskmanagement.business.entities.Comment;
import taskmanagement.business.entities.Task;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByTaskOrderByCreatedAtDesc(Task task);
}
