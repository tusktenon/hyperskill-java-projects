package engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Arrays;

@Entity
@Getter
@Setter
@NoArgsConstructor
public final class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    @Size(min = 2)
    private String[] options;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    public void setAnswer(int[] answer) {
        if (answer == null) answer = new int[0];
        Arrays.sort(answer);
        this.answer = answer;
    }
}
