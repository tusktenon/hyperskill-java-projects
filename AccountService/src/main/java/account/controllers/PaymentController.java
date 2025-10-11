package account.controllers;

import account.models.PaymentInstruction;
import account.security.SecurityUser;
import account.services.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/acct/payments")
    public Map<String, String> addPayments(
            @Valid @RequestBody List<PaymentInstruction> instructions) {
        service.addPayments(instructions);
        return Map.of("status", "Added successfully!");
    }

    @PutMapping("/acct/payments")
    public Map<String, String> updateSalary(@Valid @RequestBody PaymentInstruction instruction) {
        service.updateSalary(instruction);
        return Map.of("status", "Updated successfully!");
    }

    @GetMapping("/empl/payment")
    public Object getPayment(@AuthenticationPrincipal SecurityUser securityUser,
                             @RequestParam(required = false) String period) {
        return period != null
                ? service.getPayment(securityUser.getUser(), period)
                : service.getPayments(securityUser.getUser());
    }
}
