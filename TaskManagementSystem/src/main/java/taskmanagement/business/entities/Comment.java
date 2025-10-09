package taskmanagement.business.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Task task;

    @NotBlank
    private String text;

    @ManyToOne
    @JsonIgnore
    private Account author;

    @JsonIgnore
    private final Instant createdAt = Instant.now();

    public Comment(Task task, String text, Account author) {
        this.task = task;
        this.text = text;
        this.author = author;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("task_id")
    public Long getTaskId() {
        return task.getId();
    }

    @JsonProperty("author")
    public String getAuthorEmail() {
        return author.getEmail();
    }
}
