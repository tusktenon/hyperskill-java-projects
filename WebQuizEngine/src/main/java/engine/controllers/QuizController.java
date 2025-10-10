package engine.controllers;

import engine.models.*;
import engine.repositories.CompletionRepository;
import engine.repositories.QuizRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/quizzes")
@AllArgsConstructor
public class QuizController {

    private static final int PAGE_SIZE = 10;

    private final QuizRepository quizRepository;
    private final CompletionRepository completionRepository;

    @GetMapping
    public Page<Quiz> getAll(@RequestParam int page) {
        return quizRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable long id) {
        return ResponseEntity.of(quizRepository.findById(id));
    }

    @GetMapping("/completed")
    public Page<Completion> getCompletions(@RequestParam int page,
                                           @AuthenticationPrincipal User user) {
        return completionRepository.findByUser(
                user, PageRequest.of(page, PAGE_SIZE, Sort.by("completedAt").descending()));
    }

    @PostMapping
    public Quiz add(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal User creator) {
        quiz.setCreator(creator);
        return quizRepository.save(quiz);
    }

    @PostMapping("/{id}/solve")
    public Feedback answer(@PathVariable long id, @RequestBody Solution solution,
                           @AuthenticationPrincipal User user) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        if (solution.solves(quiz)) {
            completionRepository.save(new Completion(user, quiz));
            return new Feedback(true, "Congratulations, you're right!");
        }
        return new Feedback(false, "Wrong answer! Please, try again.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable long id, @AuthenticationPrincipal User user) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        if (user.getId() != quiz.getCreator().getId()) {
            throw new ResponseStatusException(FORBIDDEN);
        }
        quizRepository.delete(quiz);
    }
}
