package taskmanagement.presentation.models;

import jakarta.validation.constraints.NotBlank;
import taskmanagement.business.entities.*;

public record CommentSubmission(@NotBlank String text) {

    public Comment toComment(Task task, Account author) {
        return new Comment(task, text(), author);
    }
}
