package webCalendarSpring;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EventController {

    @GetMapping("/event/today")
    public List<Void> today() {
        return List.of();
    }

    @PostMapping("/event")
    public Map<String, String> addEvent(@Valid @RequestBody Event event) {
        return Map.of(
                "message", "The event has been added!",
                "event", event.event(),
                "date", event.date().toString()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleInvalidRequestBody() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
