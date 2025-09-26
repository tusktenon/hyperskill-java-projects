package account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final int minPasswordLength;
    private final Set<String> breachedPasswords;
    private final PasswordEncoder encoder;
    private final UserRepository repository;

    public UserService(@Value("${security.min-password-length}") int minPasswordLength,
                       @Value("${security.breached-passwords}") Set<String> breachedPasswords,
                       PasswordEncoder encoder,
                       UserRepository repository) {
        this.minPasswordLength = minPasswordLength;
        this.breachedPasswords = breachedPasswords;
        this.encoder = encoder;
        this.repository = repository;
    }

    public User register(User requested) {
        if (repository.existsByEmailIgnoreCase(requested.getEmail())) {
            throw new UserRegistrationException("User exist!");
        }
        validatePassword(requested.getPassword());
        requested.setPassword(encoder.encode(requested.getPassword()));
        return repository.save(requested);
    }

    public User changePassword(User user, String newPassword) {
        validatePassword(newPassword);
        if (encoder.matches(newPassword, user.getPassword())) {
            throw new InvalidPasswordException("The passwords must be different!");
        }
        user.setPassword(encoder.encode(newPassword));
        return repository.save(user);
    }

    private void validatePassword(String password) {
        if (password.length() < minPasswordLength) {
            throw new InvalidPasswordException(
                    "Password length must be %d chars minimum!".formatted(minPasswordLength));
        }
        if (breachedPasswords.contains(password)) {
            throw new InvalidPasswordException("The password is in the hacker's database!");
        }
    }
}
