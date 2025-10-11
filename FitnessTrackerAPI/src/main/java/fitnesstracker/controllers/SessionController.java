package fitnesstracker.controllers;

import fitnesstracker.models.Application;
import fitnesstracker.models.Session;
import fitnesstracker.ratelimiting.ApplicationRequestRateLimiter;
import fitnesstracker.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracker")
@AllArgsConstructor
public class SessionController {

    private final SessionRepository repository;
    private final ApplicationRequestRateLimiter limiter;

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
