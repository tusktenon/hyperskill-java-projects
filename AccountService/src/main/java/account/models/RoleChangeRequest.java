package account.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleChangeRequest(
        @NotBlank String user,
        @NotNull String role,
        @NotNull Operation operation
) {
    public enum Operation {GRANT, REMOVE}
}
