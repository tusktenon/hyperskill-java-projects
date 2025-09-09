package fitnesstracker.presentation;

import fitnesstracker.persistence.Developer;
import jakarta.validation.constraints.*;
import org.springframework.security.crypto.password.PasswordEncoder;

public record RegistrationRequest(
        @NotNull
        @Email(regexp = "\\w+([.-]\\w+)*@\\w+(\\.\\w+)+")
        String email,

        @NotBlank
        String password
) {
    public Developer toDeveloper(PasswordEncoder encoder) {
        return new Developer(email, encoder.encode(password));
    }
}
