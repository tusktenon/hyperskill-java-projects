package taskmanagement.presentation.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import taskmanagement.business.entities.*;
import taskmanagement.business.services.TaskService;
import taskmanagement.presentation.Views;
import taskmanagement.presentation.models.*;
import taskmanagement.security.AuthenticatedAccount;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    @JsonView(Views.TaskViews.WithCommentCount.class)
    List<Task> getAll() {
        return service.getAll();
    }

    @GetMapping(params = "author")
    @JsonView(Views.TaskViews.WithCommentCount.class)
    List<Task> getByAuthor(@RequestParam String author) {
        return service.getByAuthor(author);
    }

    @GetMapping(params = "assignee")
    @JsonView(Views.TaskViews.WithCommentCount.class)
    List<Task> getByAssignee(@RequestParam String assignee) {
        return service.getByAssignee(assignee);
    }

    @GetMapping(params = {"author", "assignee"})
    @JsonView(Views.TaskViews.WithCommentCount.class)
    List<Task> getByAuthorAndAssignee(@RequestParam String author, @RequestParam String assignee) {
        return service.getByAuthorAndAssignee(author, assignee);
    }

    @PostMapping
    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    Task add(@Valid @RequestBody ProposedTask proposed, @AuthenticatedAccount Account author) {
        return service.add(proposed, author);
    }

    @PutMapping("/{taskId}/assign")
    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    Task setAssignee(@PathVariable long taskId, @Valid @RequestBody AssigneeUpdate update,
                     Principal assigner) {
        return service.setAssignee(taskId, update.assignee(), assigner.getName());
    }

    @PutMapping("/{taskId}/status")
    @JsonView(Views.TaskViews.WithoutCommentCount.class)
    Task setStatus(@PathVariable long taskId, @Valid @RequestBody StatusUpdate update,
                   Principal updater) {
        return service.setStatus(taskId, update.status(), updater.getName());
    }

    @GetMapping("/{taskId}/comments")
    List<Comment> getComments(@PathVariable long taskId) {
        return service.getComments(taskId);
    }

    @PostMapping("/{taskId}/comments")
    void addComment(@PathVariable long taskId, @Valid @RequestBody CommentSubmission submission,
                    @AuthenticatedAccount Account author) {
        service.addComment(taskId, submission, author);
    }

}
