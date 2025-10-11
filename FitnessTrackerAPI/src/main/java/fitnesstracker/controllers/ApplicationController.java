package fitnesstracker.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import fitnesstracker.models.*;
import fitnesstracker.repositories.ApplicationRepository;
import fitnesstracker.security.SecurityDeveloper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationRepository repository;

    @PostMapping("/api/applications/register")
    @JsonView(Views.ApplicationViews.RegistrationSummary.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Application register(
            @Valid @RequestBody ApplicationRegistration request,
            @AuthenticationPrincipal SecurityDeveloper securityDeveloper) {
        try {
            return repository.save(
                    ApplicationMapper.convert(request, securityDeveloper.getDeveloper()));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
