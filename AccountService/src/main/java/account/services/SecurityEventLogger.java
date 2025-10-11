package account.services;

import account.models.*;
import account.repositories.SecurityEventRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static account.models.SecurityEvent.Action.*;

@Service
@AllArgsConstructor
public class SecurityEventLogger {

    private final SecurityEventRepository repository;
    private final HttpServletRequest request;

    public void logCreateUser(User created) {
        logEvent(CREATE_USER, "Anonymous", created.getEmail());
    }

    public void logChangePassword(User changed) {
        logEvent(CHANGE_PASSWORD, changed.getEmail(), changed.getEmail());
    }

    public void logAccessDenied(User denied) {
        logEvent(ACCESS_DENIED, denied.getEmail(), request.getRequestURI());
    }

    public void logLoginFailed(String username) {
        logEvent(LOGIN_FAILED, username, request.getRequestURI());
    }

    public void logGrantRole(User granted, Role role) {
        logEvent(GRANT_ROLE, request.getRemoteUser(),
                "Grant role %s to %s".formatted(role, granted.getEmail()));
    }

    public void logRemoveRole(User removed, Role role) {
        logEvent(REMOVE_ROLE, request.getRemoteUser(),
                "Remove role %s from %s".formatted(role, removed.getEmail()));
    }

    /* The Administrator has manually locked the user */
    public void logLockUserManual(User locked) {
        logEvent(LOCK_USER, request.getRemoteUser(), "Lock user " + locked.getEmail());
    }

    /* The user has been locked automatically on suspicion of a brute force attack */
    public void logLockUserAuto(User locked) {
        logEvent(LOCK_USER, locked.getEmail(), "Lock user " + locked.getEmail());
    }

    public void logUnlockUser(User unlocked) {
        logEvent(UNLOCK_USER, request.getRemoteUser(), "Unlock user " + unlocked.getEmail());
    }

    public void logDeleteUser(User deleted) {
        logEvent(DELETE_USER, request.getRemoteUser(), deleted.getEmail());
    }

    public void logBruteForce(User suspected) {
        logEvent(BRUTE_FORCE, suspected.getEmail(), request.getRequestURI());
    }

    private void logEvent(SecurityEvent.Action action, String subject, String object) {
        repository.save(new SecurityEvent(action, subject, object, request.getRequestURI()));
    }
}
