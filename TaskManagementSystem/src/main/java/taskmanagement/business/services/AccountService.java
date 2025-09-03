package taskmanagement.business.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanagement.persistence.repositories.AccountRepository;
import taskmanagement.presentation.models.RegistrationRequest;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    public AccountService(AccountRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public boolean add(RegistrationRequest request) {
        if (repository.existsByEmailIgnoreCase(request.email())) return false;
        repository.save(request.toAccount(encoder));
        return true;
    }
}
