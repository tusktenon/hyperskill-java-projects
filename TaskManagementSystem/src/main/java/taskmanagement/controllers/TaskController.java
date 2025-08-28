package taskmanagement.controllers;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import taskmanagement.models.ProposedTask;
import taskmanagement.models.Task;
import taskmanagement.repositories.AccountRepository;
import taskmanagement.repositories.TaskRepository;
import taskmanagement.security.AccountAdapter;

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
                    .map(taskRepository::findByAuthorOrderByIdDesc)
                    .orElseGet(Collections::emptyList);
        }
        return taskRepository.findAllByOrderByIdDesc();
    }

    @PostMapping("/api/tasks")
    Task add(@Valid @RequestBody ProposedTask proposed,
             @AuthenticationPrincipal AccountAdapter adapter) {
        Task task = new Task(proposed.title(), proposed.description(), adapter.getAccount());
        return taskRepository.save(task);
    }
}
