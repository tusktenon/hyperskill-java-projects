package account.controllers;

import account.models.SecurityEvent;
import account.repositories.SecurityEventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security/events/")
public class SecurityLogController {

    private final SecurityEventRepository repository;

    public SecurityLogController(SecurityEventRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SecurityEvent> getEvents() {
        return repository.findAllByOrderById();
    }
}
