package fitnesstracker.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import fitnesstracker.persistence.*;
import fitnesstracker.security.ApplicationRequestRateLimiter;
import fitnesstracker.security.SecurityDeveloper;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ApplicationController {

    private final ApplicationRepository repository;
    private final ApplicationRequestRateLimiter limiter;

    public ApplicationController(ApplicationRepository repository,
                                 ApplicationRequestRateLimiter limiter) {
        this.repository = repository;
        this.limiter = limiter;
    }

    @PostMapping("/api/applications/register")
    @JsonView(Views.ApplicationViews.RegistrationSummary.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Application register(
            @Valid @RequestBody ApplicationRegistration request,
            @AuthenticationPrincipal SecurityDeveloper securityDeveloper) {
        try {
            Application application = repository.save(
                    ApplicationMapper.convert(request, securityDeveloper.getDeveloper()));
            if (application.getCategory() == Application.Category.BASIC) {
                limiter.register(application);
            }
            return application;
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
