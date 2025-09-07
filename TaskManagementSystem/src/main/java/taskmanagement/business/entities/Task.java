package taskmanagement.business.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import taskmanagement.presentation.Views;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Task {

    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    public enum Status {CREATED, IN_PROGRESS, COMPLETED}

    @Id
    @GeneratedValue
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    private Long id;

    @NotBlank
    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    private String title;

    @NotBlank
    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    private String description;

    @NotNull
    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    private Status status = Status.CREATED;

    @ManyToOne
    private Account author;

    @ManyToOne
    private Account assignee;

    @OneToMany(mappedBy = "task")
    private final Set<Comment> comments = new HashSet<>();

    private final LocalDateTime createdAt = LocalDateTime.now();

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
    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    public String getAuthorEmail() {
        return author.getEmail();
    }

    @JsonProperty("assignee")
    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    public String getAssigneeEmail() {
        return assignee == null ? "none" : assignee.getEmail();
    }

    public void setAssignee(Account assignee) {
        this.assignee = assignee;
    }

    @JsonProperty("total_comments")
    @JsonView(Views.TaskViews.WithCommentCount.class)
    public int getCommentCount() {
        return comments.size();
    }
}
