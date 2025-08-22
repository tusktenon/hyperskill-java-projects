package engine.repositories;

import engine.models.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepository extends CrudRepository<Quiz, Long>,
        PagingAndSortingRepository<Quiz, Long> {}
