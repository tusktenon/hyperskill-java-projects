package account;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {

    private final UserService service;

    public Controller(UserService service) {
        this.service = service;
    }

    @PostMapping("/auth/changepass")
    public Map<String, String> changePassword(@Valid @RequestBody PasswordChangeRequest request,
                                              @AuthenticationPrincipal SecurityUser securityUser) {
        User updated = service.changePassword(securityUser.getUser(), request.newPassword());
        return Map.of(
                "email", updated.getEmail().toLowerCase(), // required by Hyperskill tests
                "status", "The password has been updated successfully"
        );
    }

    @PostMapping("/auth/signup")
    public User signup(@Valid @RequestBody User requested) {
        return service.register(requested);
    }

    @GetMapping("/empl/payment")
    public User getProfile(@AuthenticationPrincipal SecurityUser securityUser) {
        return securityUser.getUser();
    }
}
