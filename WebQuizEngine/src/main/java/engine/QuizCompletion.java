package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class QuizCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne
    @JsonIgnore
    private AppUser user;

    @ManyToOne
    @JsonIgnore
    private Quiz quiz;

    private LocalDateTime completedAt;

    public QuizCompletion(AppUser user, Quiz quiz, LocalDateTime completedAt) {
        this.user = user;
        this.quiz = quiz;
        this.completedAt = completedAt;
    }

    @JsonProperty("id")
    public long getQuizId() {
        return quiz.getId();
    }
}
