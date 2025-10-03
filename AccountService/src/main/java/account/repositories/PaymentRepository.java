package account.repositories;

import account.models.Payment;
import account.models.User;
import org.springframework.data.repository.CrudRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    List<Payment> findAllByEmployeeOrderByPeriodDesc(User employee);

    Optional<Payment> findByEmployeeAndPeriod(User employee, YearMonth period);
}
