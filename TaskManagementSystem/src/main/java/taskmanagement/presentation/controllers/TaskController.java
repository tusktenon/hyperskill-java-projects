package taskmanagement.presentation.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import taskmanagement.business.entities.Account;
import taskmanagement.business.entities.Task;
import taskmanagement.business.services.TaskService;
import taskmanagement.presentation.models.*;
import taskmanagement.security.AuthenticatedAccount;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    List<Task> get(@RequestParam(required = false) String author,
                   @RequestParam(required = false) String assignee) {
        return service.get(author, assignee);
    }

    @PostMapping
    Task add(@Valid @RequestBody ProposedTask proposed, @AuthenticatedAccount Account author) {
        return service.add(proposed, author);
    }

    @PutMapping("/{taskId}/assign")
    Task setAssignee(@PathVariable long taskId, @Valid @RequestBody AssigneeUpdate update,
                     Principal assigner) {
        return service.setAssignee(taskId, update.assignee(), assigner.getName());
    }

    @PutMapping("/{taskId}/status")
    Task setStatus(@PathVariable long taskId, @Valid @RequestBody StatusUpdate update,
                   Principal updater) {
        return service.setStatus(taskId, update.status(), updater.getName());
    }
}
