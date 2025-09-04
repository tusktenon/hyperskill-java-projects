package taskmanagement.business.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Task {

    public enum Status {CREATED, IN_PROGRESS, COMPLETED}

    @Id
    @GeneratedValue
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Status status = Status.CREATED;

    @ManyToOne
    private Account author;

    @ManyToOne
    private Account assignee;

    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

    public Task() {
    }

    public Task(String title, String description, Account author) {
        this.title = title;
        this.description = description;
        this.author = author;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonProperty("author")
    public String getAuthorEmail() {
        return author.getEmail();
    }

    @JsonProperty("assignee")
    public String getAssigneeEmail() {
        return assignee == null ? "none" : assignee.getEmail();
    }

    public void setAssignee(Account assignee) {
        this.assignee = assignee;
    }
}
