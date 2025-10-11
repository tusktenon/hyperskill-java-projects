package account.models;

import account.exceptions.InvalidPaymentException;
import account.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@AllArgsConstructor
public class PaymentMapper {

    private final UserRepository repository;

    public Payment convert(PaymentInstruction instruction) {
        User employee = repository.findByEmailIgnoreCase(instruction.employee())
                .orElseThrow(() -> new InvalidPaymentException(
                        "Employee email \"%s\" not found".formatted(instruction.employee())));
        return new Payment(employee, instruction.period(), instruction.salary());
    }

    public static PaymentSummary summarize(Payment payment) {
        return new PaymentSummary(
                payment.getEmployee().getFirstName(),
                payment.getEmployee().getLastName(),
                payment.getPeriod(),
                formatSalary(payment.getSalary())
        );
    }

    public static YearMonth parsePayPeriod(String periodString) {
        try {
            return YearMonth.parse(periodString, DateTimeFormatter.ofPattern("MM-yyyy"));
        } catch (DateTimeParseException e) {
            throw new InvalidPaymentException("Invalid pay period");
        }
    }

    public static String formatSalary(long salary) {
        return "%d dollar(s) %d cent(s)".formatted(salary / 100, salary % 100);
    }
}
