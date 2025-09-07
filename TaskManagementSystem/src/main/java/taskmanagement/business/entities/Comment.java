package taskmanagement.business.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ManyToOne
    private Task task;

    @NotBlank
    private String text;

    @ManyToOne
    private Account author;

    private final LocalDateTime createdAt = LocalDateTime.now();

    public Comment() {
    }

    public Comment(Task task, String text, Account author) {
        this.task = task;
        this.text = text;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("task_id")
    public Long getTaskId() {
        return task.getId();
    }

    public String getText() {
        return text;
    }

    @JsonProperty("author")
    public String getAuthorEmail() {
        return author.getEmail();
    }
}
