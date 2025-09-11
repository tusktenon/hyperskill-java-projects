package fitnesstracker.presentation;

import fitnesstracker.persistence.*;
import fitnesstracker.security.SecurityDeveloper;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/developers")
public class DeveloperController {

    private final DeveloperRepository repository;
    private final DeveloperMapper mapper;

    public DeveloperController(DeveloperRepository repository, DeveloperMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("#securityDeveloper.developer.id == #id")
    public DeveloperProfile getProfile(
            @PathVariable long id, @AuthenticationPrincipal SecurityDeveloper securityDeveloper) {
        return mapper.convert(securityDeveloper.getDeveloper());
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequest request) {
        try {
            Developer developer = repository.save(mapper.convert(request));
            URI location = URI.create("/api/developers/" + developer.getId());
            return ResponseEntity.created(location).build();
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
