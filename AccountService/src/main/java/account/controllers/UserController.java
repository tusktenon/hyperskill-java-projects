package account.controllers;

import account.models.*;
import account.security.SecurityUser;
import account.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static account.models.LockRequest.Operation.LOCK;

@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/admin/user/")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @DeleteMapping("/admin/user/{email}")
    public Map<String, String> removeUser(@PathVariable String email) {
        service.removeUser(email);
        return Map.of(
                "user", email,
                "status", "Deleted successfully!"
        );
    }

    @PutMapping("/admin/user/access")
    public Map<String, String> changeLockedStatus(@Valid @RequestBody LockRequest request) {
        User updated = service.changeLockedStatus(request);
        return Map.of("status", "User %s %s!".formatted(updated.getEmail(),
                request.operation() == LOCK ? "locked" : "unlocked"));
    }

    @PutMapping("/admin/user/role")
    public User changeRole(@Valid @RequestBody RoleChangeRequest request) {
        return service.changeRole(request);
    }

    @PostMapping("/auth/changepass")
    public Map<String, String> changePassword(@Valid @RequestBody PasswordChangeRequest request,
                                              @AuthenticationPrincipal SecurityUser securityUser) {
        User updated = service.changePassword(securityUser.getUser(), request.newPassword());
        return Map.of(
                "email", updated.getEmail(),
                "status", "The password has been updated successfully"
        );
    }

    @PostMapping("/auth/signup")
    public User signup(@Valid @RequestBody User request) {
        return service.register(request);
    }
}
