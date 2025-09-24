package account;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @PostMapping("/auth/signup")
    public User signup(@Valid @RequestBody User user) {
        return user;
    }
}
