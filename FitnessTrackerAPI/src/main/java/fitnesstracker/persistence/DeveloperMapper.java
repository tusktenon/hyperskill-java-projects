package fitnesstracker.persistence;

import fitnesstracker.presentation.DeveloperProfile;
import fitnesstracker.presentation.RegistrationRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DeveloperMapper {

    private final PasswordEncoder encoder;

    public DeveloperMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public Developer convert(RegistrationRequest request) {
        return new Developer(request.email(), encoder.encode(request.password()));
    }

    public DeveloperProfile convert(Developer developer) {
        return new DeveloperProfile(developer.getId(), developer.getEmail());
    }
}
