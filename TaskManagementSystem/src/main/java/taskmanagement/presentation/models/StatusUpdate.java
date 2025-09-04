package taskmanagement.presentation.models;

import jakarta.validation.constraints.NotNull;

import static taskmanagement.business.entities.Task.Status;

public record StatusUpdate(@NotNull Status status) {}
