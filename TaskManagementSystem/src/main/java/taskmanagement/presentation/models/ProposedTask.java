package taskmanagement.presentation.models;

import jakarta.validation.constraints.NotBlank;
import taskmanagement.business.entities.Account;
import taskmanagement.business.entities.Task;

public record ProposedTask(@NotBlank String title, @NotBlank String description) {

    public Task toTask(Account author) {
        return new Task(title(), description(), author);
    }
}
