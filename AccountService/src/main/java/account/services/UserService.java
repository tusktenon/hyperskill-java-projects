package account.services;

import account.exceptions.BadRequestException;
import account.exceptions.NotFoundException;
import account.models.*;
import account.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static account.models.Role.ADMINISTRATOR;
import static account.models.Role.USER;
import static account.models.RoleChangeRequest.Operation.GRANT;

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
            throw new BadRequestException("User exist!");
        }
        validatePassword(requested.getPassword());
        requested.setEmail(requested.getEmail().toLowerCase()); // required by Hyperskill tests
        requested.setPassword(encoder.encode(requested.getPassword()));
        requested.getRoles().add(repository.count() == 0 ? ADMINISTRATOR : USER);
        return repository.save(requested);
    }

    public User changePassword(User user, String newPassword) {
        validatePassword(newPassword);
        if (encoder.matches(newPassword, user.getPassword())) {
            throw new BadRequestException("The passwords must be different!");
        }
        user.setPassword(encoder.encode(newPassword));
        return repository.save(user);
    }

    private void validatePassword(String password) {
        if (password.length() < minPasswordLength) {
            throw new BadRequestException(
                    "Password length must be %d chars minimum!".formatted(minPasswordLength));
        }
        if (breachedPasswords.contains(password)) {
            throw new BadRequestException("The password is in the hacker's database!");
        }
    }

    public List<User> getUsers() {
        return repository.findAllByOrderById();
    }

    public User changeRole(RoleChangeRequest request) {
        User user = repository.findByEmailIgnoreCase(request.user())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        try {
            Role role = Role.valueOf(request.role());
            return request.operation() == GRANT ? addRole(user, role) : removeRole(user, role);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException("Role not found!");
        }
    }

    private User addRole(User user, Role role) {
        if (user.getRoles().stream().findAny().orElseThrow().group() != role.group()) {
            throw new BadRequestException(
                    "The user cannot combine administrative and business roles!");
        }
        user.getRoles().add(role);
        return repository.save(user);
    }

    private User removeRole(User user, Role role) {
        if (!user.getRoles().contains(role)) {
            throw new BadRequestException("The user does not have a role!");
        }
        if (role == ADMINISTRATOR) {
            throw new BadRequestException("Can't remove ADMINISTRATOR role!");
        }
        if (user.getRoles().size() == 1) {
            throw new BadRequestException("The user must have at least one role!");
        }
        user.getRoles().remove(role);
        return repository.save(user);
    }

    public void removeUser(String email) {
        User user = repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (user.getRoles().contains(ADMINISTRATOR)) {
            throw new BadRequestException("Can't remove ADMINISTRATOR role!");
        }
        repository.delete(user);
    }
}
