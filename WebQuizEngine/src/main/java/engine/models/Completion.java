package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Completion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Quiz quiz;

    private final Instant completedAt = Instant.now();

    public Completion(User user, Quiz quiz) {
        this.user = user;
        this.quiz = quiz;
    }

    @JsonProperty("id")
    public long getQuizId() {
        return quiz.getId();
    }
}
