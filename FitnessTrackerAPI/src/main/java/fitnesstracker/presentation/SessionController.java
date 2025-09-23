package fitnesstracker.presentation;

import fitnesstracker.persistence.*;
import fitnesstracker.ratelimiting.ApplicationRequestRateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracker")
public class SessionController {

    private final SessionRepository repository;
    private final ApplicationRequestRateLimiter limiter;

    public SessionController(SessionRepository repository, ApplicationRequestRateLimiter limiter) {
        this.repository = repository;
        this.limiter = limiter;
    }

    @GetMapping
    public List<Session> getSessions(@AuthenticationPrincipal Application application) {
        limiter.checkRateLimit(application);
        return repository.findAllByOrderByUploadedAtDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSession(@RequestBody Session session,
                           @AuthenticationPrincipal Application application) {
        limiter.checkRateLimit(application);
        session.setApplication(application);
        repository.save(session);
    }
}
