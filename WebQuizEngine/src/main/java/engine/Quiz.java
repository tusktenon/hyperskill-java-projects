package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
public class Quiz {

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    @ManyToOne
    @JsonIgnore
    AppUser creator;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<QuizCompletion> completions;

    public void setAnswer(int[] answer) {
        if (answer == null) answer = new int[0];
        Arrays.sort(answer);
        this.answer = answer;
    }
}
