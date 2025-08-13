package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionRepository extends CrudRepository<QuizCompletion, Long>,
        PagingAndSortingRepository<QuizCompletion, Long> {

    Page<QuizCompletion> findByUser(AppUser user, Pageable pageable);
}
