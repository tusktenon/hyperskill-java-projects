package taskmanagement.presentation.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import taskmanagement.business.services.AccountService;
import taskmanagement.presentation.models.RegistrationRequest;
import taskmanagement.security.TokenService;

import java.security.Principal;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AccountService accountService;
    private final TokenService tokenService;

    @PostMapping("/api/accounts")
    void register(@Valid @RequestBody RegistrationRequest request) {
        if (!accountService.add(request)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/api/auth/token")
    Map<String, String> getToken(Principal principal) {
        return Map.of("token", tokenService.generateToken(principal.getName()));
    }
}
