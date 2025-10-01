package account;

import jakarta.validation.constraints.*;

public record PaymentInstruction(

        @NotNull(message = "Missing value for \"employee\"")
        @Email(regexp = "\\w+@acme\\.com", message = "Invalid email")
        String employee,

        @NotNull(message = "Missing value for \"period\"")
        @Pattern(regexp = "(0[1-9]|1[0-2])-2\\d{3}", message = "Invalid pay period")
        String period,

        @NotNull(message = "Missing value for \"salary\"")
        @Min(value = 0, message = "Salary cannot be negative")
        Long salary
) {}
