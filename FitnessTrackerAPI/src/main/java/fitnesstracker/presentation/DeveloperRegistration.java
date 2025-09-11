package fitnesstracker.presentation;

import jakarta.validation.constraints.*;

public record DeveloperRegistration(
        @NotNull
        @Email(regexp = "\\w+([.-]\\w+)*@\\w+(\\.\\w+)+")
        String email,

        @NotBlank
        String password
) {}
