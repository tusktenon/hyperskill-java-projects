package taskmanagement.business.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanagement.persistence.repositories.AccountRepository;
import taskmanagement.presentation.models.RegistrationRequest;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    public boolean add(RegistrationRequest request) {
        if (repository.existsByEmailIgnoreCase(request.email())) return false;
        repository.save(request.toAccount(encoder));
        return true;
    }
}
