package account.controllers;

import account.models.SecurityEvent;
import account.repositories.SecurityEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security/events/")
@AllArgsConstructor
public class SecurityLogController {

    private final SecurityEventRepository repository;

    @GetMapping
    public List<SecurityEvent> getEvents() {
        return repository.findAllByOrderById();
    }
}
