package fitnesstracker.presentation;

import fitnesstracker.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracker")
public class SessionController {

    private final SessionRepository repository;

    public SessionController(SessionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Session> getSessions() {
        return repository.findAllByOrderByUploadedAtDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSession(@RequestBody Session session,
                           @AuthenticationPrincipal Application application) {
        session.setApplication(application);
        repository.save(session);
    }
}
