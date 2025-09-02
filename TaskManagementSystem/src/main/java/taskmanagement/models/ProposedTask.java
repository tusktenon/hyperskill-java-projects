package taskmanagement.models;

import jakarta.validation.constraints.NotBlank;

public record ProposedTask(@NotBlank String title, @NotBlank String description) {

    public Task toTask(Account author) {
        return new Task(title(), description(), author);
    }
}
