package fitnesstracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracker")
public class TrackerController {

    private final SessionRepository repository;

    public TrackerController(SessionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Session> getSessions() {
        return repository.findAllByOrderByUploadedAtDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSession(@RequestBody Session session) {
        repository.save(session);
    }
}
