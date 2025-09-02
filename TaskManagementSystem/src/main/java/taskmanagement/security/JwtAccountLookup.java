package taskmanagement.security;

import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.stereotype.Service;
import taskmanagement.models.Account;
import taskmanagement.repositories.AccountRepository;

@Service
public class JwtAccountLookup {

    private final AccountRepository repository;

    public JwtAccountLookup(AccountRepository repository) {
        this.repository = repository;
    }

    public Account getAccount(JwtClaimAccessor token) {
        return repository.findByEmailIgnoreCase(token.getSubject()).orElseThrow();
    }
}
