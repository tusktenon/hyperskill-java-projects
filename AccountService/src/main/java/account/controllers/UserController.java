package account.controllers;

import account.models.*;
import account.security.SecurityUser;
import account.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/admin/user/") // Hyperskill tests require "user/", not "user"
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
    public User signup(@Valid @RequestBody User requested) {
        return service.register(requested);
    }
}
