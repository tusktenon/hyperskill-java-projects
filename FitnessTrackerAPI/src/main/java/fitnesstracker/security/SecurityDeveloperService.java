package fitnesstracker.security;

import fitnesstracker.persistence.DeveloperRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class SecurityDeveloperService implements UserDetailsService {

    private final DeveloperRepository repository;

    public SecurityDeveloperService(DeveloperRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .map(SecurityDeveloper::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }
}
