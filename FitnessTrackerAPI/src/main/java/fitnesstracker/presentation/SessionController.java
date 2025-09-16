package fitnesstracker.presentation;

import fitnesstracker.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/tracker")
public class SessionController {

    private final ApplicationRepository applicationRepository;
    private final SessionRepository sessionRepository;

    public SessionController(ApplicationRepository applicationRepository,
                             SessionRepository sessionRepository) {
        this.applicationRepository = applicationRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public List<Session> getSessions(
            @RequestHeader(name = "X-API-Key", required = false) String keyHeader) {
        Application application = getApplicationByApiKey(keyHeader);
        return sessionRepository.findAllByOrderByUploadedAtDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSession(@RequestHeader(value = "X-API-Key", required = false) String keyHeader,
                           @RequestBody Session session) {
        Application application = getApplicationByApiKey(keyHeader);
        session.setApplication(application);
        sessionRepository.save(session);
    }

    private Application getApplicationByApiKey(String keyString) {
        try {
            UUID key = UUID.fromString(Objects.requireNonNull(keyString));
            return applicationRepository.findByApiKey(key).orElseThrow();
        } catch (NullPointerException | IllegalArgumentException | NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
