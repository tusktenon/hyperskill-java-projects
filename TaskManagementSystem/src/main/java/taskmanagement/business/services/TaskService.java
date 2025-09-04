package taskmanagement.business.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    public List<Task> get(String author, String assignee) {
        try {
            if (assignee != null) {
                Account assigneeAccount = findAssigneeAccountByUsername(assignee);
                if (author != null) {
                    Account authorAccount = findAccountByUsername(author);
                    return taskRepository.findByAuthorAndAssigneeOrderByCreatedAtDesc(
                            authorAccount, assigneeAccount);
                } else {
                    return taskRepository.findByAssigneeOrderByCreatedAtDesc(assigneeAccount);
                }
            }
            if (author != null) {
                Account authorAccount = findAccountByUsername(author);
                return taskRepository.findByAuthorOrderByCreatedAtDesc(authorAccount);
            }
        } catch (NotFoundException e) {
            return Collections.emptyList();
        }
        return taskRepository.findAllByOrderByCreatedAtDesc();
    }

    public Task add(ProposedTask proposed, Account author) {
        return taskRepository.save(proposed.toTask(author));
    }

    public Task setAssignee(long taskId, String assignee, String assigner) {
        Task task = findTaskById(taskId);
        if (!task.getAuthorEmail().equals(assigner)) {
            throw new UnauthorizedAccountException();
        }
        task.setAssignee(findAssigneeAccountByUsername(assignee));
        return taskRepository.save(task);
    }

    public Task setStatus(long taskId, Task.Status status, String updater) {
        Task task = findTaskById(taskId);
        if (!task.getAuthorEmail().equals(updater)
                && !task.getAssigneeEmail().equals(updater)) {
            throw new UnauthorizedAccountException();
        }
        task.setStatus(status);
        return taskRepository.save(task);
    }

    private Account findAccountByUsername(String username) {
        return accountRepository.findByEmailIgnoreCase(username)
                .orElseThrow(NotFoundException::new);
    }

    private Account findAssigneeAccountByUsername(String username) {
        return "none".equals(username) ? null : findAccountByUsername(username);
    }

    private Task findTaskById(long id) {
        return taskRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {}

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class UnauthorizedAccountException extends RuntimeException {}
}
