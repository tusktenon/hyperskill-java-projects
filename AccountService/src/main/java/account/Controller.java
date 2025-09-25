package account;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class Controller {

    private final PasswordEncoder encoder;
    private final UserRepository repository;

    public Controller(PasswordEncoder encoder, UserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    @PostMapping("/auth/signup")
    public User signup(@Valid @RequestBody User registration) {
        if (repository.existsByEmailIgnoreCase(registration.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
        registration.setPassword(encoder.encode(registration.getPassword()));
        return repository.save(registration);
    }

    @GetMapping("/empl/payment")
    public User getProfile(@AuthenticationPrincipal SecurityUser securityUser) {
        return securityUser.getUser();
    }
}
