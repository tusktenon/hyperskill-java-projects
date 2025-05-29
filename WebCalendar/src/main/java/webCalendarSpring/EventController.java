package webCalendarSpring;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/event")
    public ResponseEntity<?> allEvents() {
        List<Event> events = eventRepository.findAllEvents();
        return events.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/event/today")
    public List<Event> today() {
        return eventRepository.findByDate(LocalDate.now());
    }

    @PostMapping("/event")
    public Map<String, String> addEvent(@Valid @RequestBody Event event) {
        Event saved = eventRepository.save(event);
        return Map.of(
                "message", "The event has been added!",
                "event", saved.getEvent(),
                "date", saved.getDate().toString()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleInvalidRequestBody() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
