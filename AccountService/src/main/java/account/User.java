package account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {

    @NotBlank
    @JsonProperty("name")
    private String firstName;

    @NotBlank
    @JsonProperty("lastname")
    private String lastName;

    @Email(regexp = "\\w+@acme\\.com")
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
