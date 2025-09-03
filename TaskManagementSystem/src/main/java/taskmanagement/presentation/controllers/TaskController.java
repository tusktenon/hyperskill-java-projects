package taskmanagement.presentation.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import taskmanagement.business.entities.Account;
import taskmanagement.business.entities.Task;
import taskmanagement.business.services.TaskService;
import taskmanagement.presentation.models.ProposedTask;
import taskmanagement.security.AuthenticatedAccount;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/api/tasks")
    List<Task> getAll(@RequestParam(required = false) String author) {
        return author == null ? service.getAll() : service.get(author);
    }

    @PostMapping("/api/tasks")
    Task add(@Valid @RequestBody ProposedTask proposed, @AuthenticatedAccount Account author) {
        return service.add(proposed, author);
    }
}
