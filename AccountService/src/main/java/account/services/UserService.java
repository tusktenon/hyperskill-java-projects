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

import static account.models.LockRequest.Operation.LOCK;
import static account.models.Role.ADMINISTRATOR;
import static account.models.Role.USER;
import static account.models.RoleChangeRequest.Operation.GRANT;

@Service
public class UserService {

    private final UserRepository repository;
    private final SecurityEventLogger logger;
    private final PasswordEncoder encoder;
    private final int minPasswordLength;
    private final Set<String> breachedPasswords;

    public UserService(
            UserRepository repository,
            SecurityEventLogger logger,
            PasswordEncoder encoder,
            @Value("${security.min-password-length}") int minPasswordLength,
            @Value("${security.breached-passwords}") Set<String> breachedPasswords
    ) {
        this.repository = repository;
        this.logger = logger;
        this.encoder = encoder;
        this.minPasswordLength = minPasswordLength;
        this.breachedPasswords = breachedPasswords;
    }

    public User register(User requested) {
        if (repository.existsByEmailIgnoreCase(requested.getEmail())) {
            throw new BadRequestException("User exist!");
        }
        validatePassword(requested.getPassword());
        requested.setEmail(requested.getEmail().toLowerCase()); // required by Hyperskill tests
        requested.setPassword(encoder.encode(requested.getPassword()));
        requested.getRoles().add(repository.count() == 0 ? ADMINISTRATOR : USER);
        requested.setNonLocked(true);
        User created = repository.save(requested);
        logger.logCreateUser(created);
        return created;
    }

    public User changePassword(User user, String newPassword) {
        validatePassword(newPassword);
        if (encoder.matches(newPassword, user.getPassword())) {
            throw new BadRequestException("The passwords must be different!");
        }
        user.setPassword(encoder.encode(newPassword));
        User changed = repository.save(user);
        logger.logChangePassword(changed);
        return changed;
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
        User target = repository.findByEmailIgnoreCase(request.user())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        try {
            Role role = Role.valueOf(request.role());
            return request.operation() == GRANT ? addRole(target, role) : removeRole(target, role);
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
        User updated = repository.save(user);
        logger.logGrantRole(updated, role);
        return updated;
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
        User updated = repository.save(user);
        logger.logRemoveRole(updated, role);
        return updated;
    }

    public User changeLockedStatus(LockRequest request) {
        User target = repository.findByEmailIgnoreCase(request.user())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        return request.operation() == LOCK ? lockUser(target) : unlockUser(target);
    }

    private User lockUser(User user) {
        if (user.getRoles().contains(ADMINISTRATOR)) {
            throw new BadRequestException("Can't lock the ADMINISTRATOR!");
        }
        user.setNonLocked(false);
        User locked = repository.save(user);
        logger.logLockUserManual(locked);
        return locked;
    }

    private User unlockUser(User user) {
        user.setNonLocked(true);
        user.resetLoginAttempts();
        User unlocked = repository.save(user);
        logger.logUnlockUser(unlocked);
        return unlocked;
    }

    public void removeUser(String request) {
        User target = repository.findByEmailIgnoreCase(request)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (target.getRoles().contains(ADMINISTRATOR)) {
            throw new BadRequestException("Can't remove ADMINISTRATOR role!");
        }
        repository.delete(target);
        logger.logDeleteUser(target);
    }
}
