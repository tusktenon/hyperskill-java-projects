package account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public record PasswordChangeRequest(@NotEmpty @JsonProperty("new_password") String newPassword) {}
