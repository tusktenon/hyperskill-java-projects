package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
public class Chef {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    @NotNull
    @Email(regexp = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+")
    private String email;

    @NotBlank
    @Size(min = 8)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Setter
    private String password;
}
