package taskmanagement.business.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Task {

    public enum Status {CREATED}

    @Id
    @GeneratedValue
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Status status;

    @ManyToOne
    private Account author;

    @JsonIgnore
    private LocalDateTime createdAt;

    public Task() {
    }

    public Task(String title, String description, Account author) {
        this.title = title;
        this.description = description;
        this.status = Status.CREATED;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    @JsonProperty("author")
    public String getAuthorEmail() {
        return author.getEmail();
    }
}
