package fitnesstracker.presentation;

import jakarta.validation.constraints.*;

public record RegistrationRequest(
        @NotNull
        @Email(regexp = "\\w+([.-]\\w+)*@\\w+(\\.\\w+)+")
        String email,

        @NotBlank
        String password
) {}
