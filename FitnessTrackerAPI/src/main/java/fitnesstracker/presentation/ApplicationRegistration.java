package fitnesstracker.presentation;

import fitnesstracker.persistence.Application;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApplicationRegistration(
        @NotBlank String name,
        @NotNull String description,
        @NotNull Application.Category category
) {}
