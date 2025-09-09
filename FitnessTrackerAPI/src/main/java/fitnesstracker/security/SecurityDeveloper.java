package fitnesstracker.security;

import fitnesstracker.persistence.Developer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SecurityDeveloper implements UserDetails {

    private final Developer developer;

    public SecurityDeveloper(Developer developer) {
        this.developer = developer;
    }

    public Developer getDeveloper() {
        return developer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return developer.getPassword();
    }

    @Override
    public String getUsername() {
        return developer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
