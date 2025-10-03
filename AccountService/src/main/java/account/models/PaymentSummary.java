package account.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.YearMonth;

public record PaymentSummary(
        String name,
        String lastname,
        @JsonFormat(pattern = "MMMM-yyyy") YearMonth period,
        String salary
) {}
