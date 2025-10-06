package account.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

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

    @Column(unique = true)
    @NotNull
    @Email(regexp = "\\w+@acme\\.com")
    @Setter
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Setter
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private final Set<Role> roles = EnumSet.noneOf(Role.class);

    @JsonIgnore
    private int failedLoginAttempts;

    @JsonIgnore
    @Setter
    private boolean isNonLocked;

    @JsonProperty("roles")
    public List<String> getRolesSorted() {
        return roles.stream().map(Role::toStringWithPrefix).sorted().toList();
    }

    public void resetLoginAttempts() {
        failedLoginAttempts = 0;
    }

    public void incrementLoginAttempts() {
        failedLoginAttempts++;
    }
}
