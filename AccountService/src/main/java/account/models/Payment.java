package account.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.YearMonth;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"EMPLOYEE_ID", "PERIOD"}))
@NoArgsConstructor
@Getter
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "EMPLOYEE_ID")
    private User employee;

    @Column(name = "PERIOD", nullable = false)
    private YearMonth period;

    @NotNull
    @Min(0)
    @Setter
    private Long salary;

    Payment(User employee, YearMonth period, Long salary) {
        this.employee = employee;
        this.period = period;
        this.salary = salary;
    }
}
