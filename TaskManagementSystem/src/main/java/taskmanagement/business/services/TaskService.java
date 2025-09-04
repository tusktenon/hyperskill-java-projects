package taskmanagement.business.services;

import org.springframework.stereotype.Service;
import taskmanagement.business.entities.Account;
import taskmanagement.business.entities.Task;
import taskmanagement.persistence.repositories.AccountRepository;
import taskmanagement.persistence.repositories.TaskRepository;
import taskmanagement.presentation.models.ProposedTask;

import java.util.*;

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
                Account assigneeAccount = "none".equals(assignee)
                        ? null
                        : findAccountByUsername(assignee).orElseThrow();
                if (author != null) {
                    Account authorAccount = findAccountByUsername(author).orElseThrow();
                    return taskRepository.findByAuthorAndAssigneeOrderByCreatedAtDesc(
                            authorAccount, assigneeAccount);
                } else {
                    return taskRepository.findByAssigneeOrderByCreatedAtDesc(assigneeAccount);
                }
            }
            if (author != null) {
                Account authorAccount = findAccountByUsername(author).orElseThrow();
                return taskRepository.findByAuthorOrderByCreatedAtDesc(authorAccount);
            }
        } catch (NoSuchElementException e) {
            return Collections.emptyList();
        }
        return taskRepository.findAllByOrderByCreatedAtDesc();
    }

    public Task add(ProposedTask proposed, Account author) {
        return taskRepository.save(proposed.toTask(author));
    }

    public Task setAssignee(long taskId, String assignee, String assigner) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        if (!task.getAuthorEmail().equals(assigner)) {
            throw new UnauthorizedAccountException();
        }
        Account assigneeAccount = "none".equals(assignee)
                ? null
                : findAccountByUsername(assignee).orElseThrow();
        task.setAssignee(assigneeAccount);
        return taskRepository.save(task);
    }

    public Task setStatus(long taskId, Task.Status status, String updater) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        if (!task.getAuthorEmail().equals(updater)
                && !task.getAssigneeEmail().equals(updater)) {
            throw new UnauthorizedAccountException();
        }
        task.setStatus(status);
        return taskRepository.save(task);
    }

    private Optional<Account> findAccountByUsername(String username) {
        return accountRepository.findByEmailIgnoreCase(username);
    }

    public static class UnauthorizedAccountException extends RuntimeException {}
}
