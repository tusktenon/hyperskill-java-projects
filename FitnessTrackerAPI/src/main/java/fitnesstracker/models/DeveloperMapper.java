package fitnesstracker.models;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeveloperMapper {

    private final PasswordEncoder encoder;

    public Developer convert(DeveloperRegistration request) {
        return new Developer(request.email(), encoder.encode(request.password()));
    }
}
