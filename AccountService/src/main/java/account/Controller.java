package account;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
public class Controller {

    private final PaymentService paymentService;
    private final UserService userService;

    public Controller(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @PostMapping("/acct/payments")
    public Map<String, String> addPayments(
            @Valid @RequestBody List<PaymentInstruction> instructions) {
        paymentService.addPayments(instructions);
        return Map.of("status", "Added successfully!");
    }

    @PutMapping("/acct/payments")
    public Map<String, String> updateSalary(@Valid @RequestBody PaymentInstruction instruction) {
        paymentService.updateSalary(instruction);
        return Map.of("status", "Updated successfully!");
    }

    @PostMapping("/auth/changepass")
    public Map<String, String> changePassword(@Valid @RequestBody PasswordChangeRequest request,
                                              @AuthenticationPrincipal SecurityUser securityUser) {
        User updated = userService.changePassword(securityUser.getUser(), request.newPassword());
        return Map.of(
                "email", updated.getEmail().toLowerCase(), // required by Hyperskill tests
                "status", "The password has been updated successfully"
        );
    }

    @PostMapping("/auth/signup")
    public User signup(@Valid @RequestBody User requested) {
        return userService.register(requested);
    }

    @GetMapping("/empl/payment")
    public Object getPayment(@AuthenticationPrincipal SecurityUser securityUser,
                             @RequestParam(required = false) String period) {
        return period != null
                ? paymentService.getPayment(securityUser.getUser(), period)
                : paymentService.getPayments(securityUser.getUser());
    }
}
