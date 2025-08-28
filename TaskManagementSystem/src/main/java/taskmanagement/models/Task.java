package taskmanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Task {

    public enum Status {CREATED}

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Status status;

    @ManyToOne
    private Account author;

    public Task() {
    }

    public Task(String title, String description, Account author) {
        this.title = title;
        this.description = description;
        this.status = Status.CREATED;
        this.author = author;
    }

    @JsonProperty("id")
    public String getIdAsString() {
        return id.toString();
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
