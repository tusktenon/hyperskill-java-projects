package account;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.YearMonth;

public record PaymentInstruction(

        @NotNull(message = "Missing value for \"employee\"")
        @Email(regexp = "\\w+@acme\\.com", message = "Invalid email")
        String employee,

        @NotNull(message = "Missing value for \"period\"")
        @JsonFormat(pattern = "MM-yyyy")
        YearMonth period,

        @NotNull(message = "Missing value for \"salary\"")
        @Min(value = 0, message = "Salary cannot be negative")
        Long salary
) {}
