package taskmanagement.business.services;

import org.springframework.stereotype.Service;
import taskmanagement.business.entities.Account;
import taskmanagement.business.entities.Task;
import taskmanagement.persistence.repositories.AccountRepository;
import taskmanagement.persistence.repositories.TaskRepository;
import taskmanagement.presentation.models.ProposedTask;

import java.util.Collections;
import java.util.List;

@Service
public class TaskService {

    private final AccountRepository accountRepository;
    private final TaskRepository taskRepository;

    public TaskService(AccountRepository accountRepository, TaskRepository taskRepository) {
        this.accountRepository = accountRepository;
        this.taskRepository = taskRepository;
    }

    public List<Task> getAll() {
        return taskRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Task> get(String author) {
        return accountRepository.findByEmailIgnoreCase(author)
                .map(taskRepository::findByAuthorOrderByCreatedAtDesc)
                .orElseGet(Collections::emptyList);
    }

    public Task add(ProposedTask proposed, Account author) {
        return taskRepository.save(proposed.toTask(author));
    }
}
