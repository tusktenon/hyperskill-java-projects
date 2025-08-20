package webCalendarSpring;

import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends ListCrudRepository<Event, Long> {

    List<Event> findByDate(LocalDate date);

    List<Event> findByDateBetween(LocalDate start, LocalDate end);
}
