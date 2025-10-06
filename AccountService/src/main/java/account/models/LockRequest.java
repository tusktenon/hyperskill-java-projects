package account.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LockRequest(@NotBlank String user, @NotNull Operation operation) {

    public enum Operation {LOCK, UNLOCK}
}
