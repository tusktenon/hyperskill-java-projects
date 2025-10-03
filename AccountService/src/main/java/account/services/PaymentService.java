package account.services;

import account.exceptions.InvalidPaymentException;
import account.models.*;
import account.repositories.PaymentRepository;
import account.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentMapper mapper;
    private final PaymentRepository repository;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository) {
        mapper = new PaymentMapper(userRepository);
        repository = paymentRepository;
    }

    public void addPayments(List<PaymentInstruction> instructions) {
        try {
            List<Payment> payments = instructions.stream().map(mapper::convert).toList();
            repository.saveAll(payments); // CrudRepository.saveAll is transactional
        } catch (DataIntegrityViolationException ex) {
            throw new InvalidPaymentException("Non-unique employee-period pair");
        }
    }

    public void updateSalary(PaymentInstruction instruction) {
        Payment requested = mapper.convert(instruction);
        Payment current = repository
                .findByEmployeeAndPeriod(requested.getEmployee(), requested.getPeriod())
                .orElseThrow(() -> new InvalidPaymentException("No such payment found"));
        current.setSalary(requested.getSalary());
        repository.save(current);
    }

    public Optional<PaymentSummary> getPayment(User employee, String period) {
        YearMonth parsed = PaymentMapper.parsePayPeriod(period);
        return repository.findByEmployeeAndPeriod(employee, parsed)
                .map(PaymentMapper::summarize);
    }

    public List<PaymentSummary> getPayments(User employee) {
        return repository.findAllByEmployeeOrderByPeriodDesc(employee)
                .stream().map(PaymentMapper::summarize).toList();
    }
}
