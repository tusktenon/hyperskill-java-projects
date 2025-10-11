package fitnesstracker.security;

import fitnesstracker.models.Application;
import fitnesstracker.repositories.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.UUID;

@AllArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final ApplicationRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UUID key = (UUID) authentication.getCredentials();
        Application application = repository.findByApiKey(key)
                .orElseThrow(() -> new BadCredentialsException(
                        "Invalid API Key: matching application not found"));
        return new ApiKeyAuthentication(application, key);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthentication.class.equals(authentication);
    }
}
