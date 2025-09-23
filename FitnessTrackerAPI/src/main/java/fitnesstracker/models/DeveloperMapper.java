package fitnesstracker.models;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DeveloperMapper {

    private final PasswordEncoder encoder;

    public DeveloperMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public Developer convert(DeveloperRegistration request) {
        return new Developer(request.email(), encoder.encode(request.password()));
    }
}
