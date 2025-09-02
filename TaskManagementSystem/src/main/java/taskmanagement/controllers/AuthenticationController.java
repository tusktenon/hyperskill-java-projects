package taskmanagement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import taskmanagement.models.Account;
import taskmanagement.models.RegistrationRequest;
import taskmanagement.repositories.AccountRepository;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
public class AuthenticationController {

    @Value("${authentication.tokenExpiration}")
    long tokenExpiration;

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthenticationController(AccountRepository repository, PasswordEncoder passwordEncoder,
                                    JwtEncoder jwtEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/api/accounts")
    void register(@Valid @RequestBody RegistrationRequest request) {
        if (repository.existsByEmailIgnoreCase(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Account account = request.toAccount(passwordEncoder);
        repository.save(account);
    }

    @PostMapping("/api/auth/token")
    Map<String, String> getToken(Principal principal) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(principal.getName())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(tokenExpiration, ChronoUnit.SECONDS))
                .build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
        return Map.of("token", token);
    }
}
