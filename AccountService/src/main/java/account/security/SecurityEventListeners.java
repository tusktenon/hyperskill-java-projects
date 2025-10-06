package account.security;

import account.models.User;
import account.repositories.UserRepository;
import account.services.SecurityEventLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

import static account.models.Role.ADMINISTRATOR;

@Component
public class SecurityEventListeners {

    private final SecurityEventLogger logger;
    private final UserRepository repository;
    private final int maxFailedLoginAttempts;

    public SecurityEventListeners(
            SecurityEventLogger logger,
            UserRepository repository,
            @Value("${security.max-failed-login-attempts}") int maxFailedLoginAttempts
    ) {
        this.logger = logger;
        this.repository = repository;
        this.maxFailedLoginAttempts = maxFailedLoginAttempts;
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        logger.logLoginFailed(username);
        repository.findByEmailIgnoreCase(username).ifPresent(user -> {
            user.incrementLoginAttempts();
            if (user.getFailedLoginAttempts() > maxFailedLoginAttempts
                    && !user.getRoles().contains(ADMINISTRATOR)) {
                logger.logBruteForce(user);
                user.setNonLocked(false);
                logger.logLockUserAuto(user);
            }
            repository.save(user);
        });
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        User user = ((SecurityUser) event.getAuthentication().getPrincipal()).getUser();
        if (user.getFailedLoginAttempts() > 0) {
            user.resetLoginAttempts();
            repository.save(user);
        }
    }

    @EventListener
    public void onAccessDenied(AuthorizationDeniedEvent<?> event) {
        // access can be denied because the request was made without authentication, or because
        // an authenticated user lacks the authority to access the endpoint; we only log the latter
        Object principal = event.getAuthentication().get().getPrincipal();
        if (principal instanceof SecurityUser) {
            User user = ((SecurityUser) principal).getUser();
            logger.logAccessDenied(user);
        }
    }
}
