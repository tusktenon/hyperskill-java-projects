package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue
    @JsonIgnore
    @Setter
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @Column(nullable = false)
    @Setter
    private Instant date;

    @NotBlank
    private String description;

    @NotNull
    @Size(min = 1)
    private String[] ingredients;

    @NotNull
    @Size(min = 1)
    private String[] directions;
}
