package taskmanagement.presentation.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AssigneeUpdate(
        @NotNull
        @Pattern(regexp = "none|\\w+([.-]\\w+)*@\\w+(\\.\\w+)+")
        String assignee
) {}
