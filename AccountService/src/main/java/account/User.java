package account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "USERS") // "USER" is a SQL reserved word
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @JsonProperty("name")
    private String firstName;

    @NotBlank
    @JsonProperty("lastname")
    private String lastName;

    @NotNull
    @Email(regexp = "\\w+@acme\\.com")
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Setter
    private String password;
}
