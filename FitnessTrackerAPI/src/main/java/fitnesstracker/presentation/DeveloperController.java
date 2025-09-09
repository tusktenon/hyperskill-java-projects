package fitnesstracker.presentation;

import fitnesstracker.persistence.Developer;
import fitnesstracker.persistence.DeveloperRepository;
import fitnesstracker.security.SecurityDeveloper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/api/developers")
public class DeveloperController {

    private final DeveloperRepository repository;
    private final PasswordEncoder encoder;

    public DeveloperController(DeveloperRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/{id}")
    public Developer getProfile(@PathVariable long id,
                                @AuthenticationPrincipal SecurityDeveloper securityDeveloper) {
        Developer developer = securityDeveloper.getDeveloper();
        if (developer.getId() != id) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return developer;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequest request) {
        if (repository.existsByEmail(request.email())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Developer developer = repository.save(request.toDeveloper(encoder));
        return ResponseEntity.created(URI.create("/api/developers/" + developer.getId())).build();
    }
}
