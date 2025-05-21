package webCalendarSpring;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record Event(@NotBlank String event, @NotNull LocalDate date) {}
