package webCalendarSpring;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {

    public default List<Event> findAllEvents() {
        List<Event> events = new ArrayList<>((int) count());
        findAll().forEach(events::add);
        return events;
    }

    public List<Event> findByDate(LocalDate date);
}
