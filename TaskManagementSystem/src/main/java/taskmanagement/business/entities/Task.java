package taskmanagement.business.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import taskmanagement.presentation.Views;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
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
    @Setter
    private Status status = Status.CREATED;

    @ManyToOne
    @JsonIgnore
    private Account author;

    @ManyToOne
    @Setter
    @JsonIgnore
    private Account assignee;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private final Set<Comment> comments = new HashSet<>();

    @JsonIgnore
    private final Instant createdAt = Instant.now();

    public Task(String title, String description, Account author) {
        this.title = title;
        this.description = description;
        this.author = author;
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

    @JsonProperty("total_comments")
    @JsonView(Views.TaskViews.WithCommentCount.class)
    public int getCommentCount() {
        return comments.size();
    }
}
