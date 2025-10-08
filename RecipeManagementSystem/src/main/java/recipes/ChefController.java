package recipes;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ChefController {

    private final ChefRepository repository;
    private final PasswordEncoder encoder;

    public ChefController(ChefRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody Chef chef) {
        if (repository.existsByEmail(chef.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        chef.setPassword(encoder.encode(chef.getPassword()));
        repository.save(chef);
    }
}
