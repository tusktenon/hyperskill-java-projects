package engine.repositories;

import engine.models.Completion;
import engine.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionRepository extends CrudRepository<Completion, Long>,
        PagingAndSortingRepository<Completion, Long> {

    Page<Completion> findByUser(User user, Pageable pageable);
}
