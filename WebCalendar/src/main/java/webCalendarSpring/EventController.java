package webCalendarSpring;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/event")
    public ResponseEntity<?> getEventsInDateRange(
            @RequestParam(name = "start_time", required = false) LocalDate start,
            @RequestParam(name = "end_time", required = false) LocalDate end
    ) {
        List<Event> events = start == null && end == null
                ? eventRepository.findAll()
                : eventRepository.findByDateBetween(start, end);
        return events.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventRepository.findById(id).orElseThrow());
    }

    @GetMapping("/event/today")
    public List<Event> getEventsForToday() {
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

    @DeleteMapping("/event/{id}")
    public ResponseEntity<Event> deleteEventById(@PathVariable Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        eventRepository.delete(event);
        return ResponseEntity.ok(event);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleInvalidRequestBody() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchEvent() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "The event doesn't exist!"));
    }
}
