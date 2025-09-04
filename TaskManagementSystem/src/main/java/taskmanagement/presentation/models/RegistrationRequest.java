package taskmanagement.presentation.models;

import jakarta.validation.constraints.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import taskmanagement.business.entities.Account;

public record RegistrationRequest(
        @NotNull
        @Email(regexp = "\\w+([.-]\\w+)*@\\w+(\\.\\w+)+")
        String email,

        @NotBlank
        @Size(min = 6)
        String password
) {
    public Account toAccount(PasswordEncoder encoder) {
        return new Account(email, encoder.encode(password));
    }
}
