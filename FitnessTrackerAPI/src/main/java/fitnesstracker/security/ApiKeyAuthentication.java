package fitnesstracker.security;

import fitnesstracker.models.Application;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@AllArgsConstructor
public class ApiKeyAuthentication implements Authentication {

    private final Application application;
    private final UUID key;

    ApiKeyAuthentication(UUID key) {
        this(null, key);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public UUID getCredentials() {
        return key;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Application getPrincipal() {
        return application;
    }

    @Override
    public boolean isAuthenticated() {
        return application != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) throw new IllegalArgumentException();
    }

    @Override
    public String getName() {
        return application != null ? application.getName() : null;
    }
}
