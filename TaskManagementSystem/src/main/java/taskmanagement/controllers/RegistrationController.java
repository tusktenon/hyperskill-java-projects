package taskmanagement.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import taskmanagement.models.Account;
import taskmanagement.models.RegistrationRequest;
import taskmanagement.repositories.AccountRepository;

@RestController
public class RegistrationController {

    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    public RegistrationController(AccountRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostMapping("/api/accounts")
    void register(@Valid @RequestBody RegistrationRequest request) {
        if (repository.existsByEmailIgnoreCase(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Account account = request.toAccount(encoder);
        repository.save(account);
    }
}
