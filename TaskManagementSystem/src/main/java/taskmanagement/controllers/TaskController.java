package taskmanagement.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import taskmanagement.models.*;
import taskmanagement.repositories.AccountRepository;
import taskmanagement.repositories.TaskRepository;
import taskmanagement.security.AuthenticatedAccount;

import java.util.Collections;
import java.util.List;

@RestController
public class TaskController {

    private final AccountRepository accountRepository;
    private final TaskRepository taskRepository;

    public TaskController(AccountRepository accountRepository, TaskRepository taskRepository) {
        this.accountRepository = accountRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/api/tasks")
    List<Task> getAll(@RequestParam(name = "author", required = false) String email) {
        if (email != null) {
            return accountRepository.findByEmailIgnoreCase(email)
                    .map(taskRepository::findByAuthorOrderByCreatedAtDesc)
                    .orElseGet(Collections::emptyList);
        }
        return taskRepository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping("/api/tasks")
    Task add(@Valid @RequestBody ProposedTask proposed, @AuthenticatedAccount Account author) {
        return taskRepository.save(proposed.toTask(author));
    }
}
