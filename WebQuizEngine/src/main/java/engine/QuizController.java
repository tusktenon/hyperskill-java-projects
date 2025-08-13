package engine;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private static final int PAGE_SIZE = 10;

    private final QuizRepository quizRepository;
    private final CompletionRepository completionRepository;

    public QuizController(QuizRepository quizRepository,
                          CompletionRepository completionRepository) {
        this.quizRepository = quizRepository;
        this.completionRepository = completionRepository;
    }

    @GetMapping
    public Page<Quiz> getAll(@RequestParam int page) {
        return quizRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable long id) {
        return ResponseEntity.of(quizRepository.findById(id));
    }

    @GetMapping("/completed")
    public Page<QuizCompletion> getCompletions(@RequestParam int page,
                                               @AuthenticationPrincipal AppUser user) {
        return completionRepository.findByUserId(
                user.getId(), PageRequest.of(page, PAGE_SIZE, Sort.by("completedAt").descending()));
    }

    @PostMapping
    public Quiz add(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal AppUser creator) {
        quiz.setCreator(creator);
        return quizRepository.save(quiz);
    }

    @PostMapping("/{id}/solve")
    public QuizResult answerQuiz(@PathVariable long id, @RequestBody Solution solution,
                                 @AuthenticationPrincipal AppUser user) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        if (solution.solves(quiz)) {
            completionRepository.save(new QuizCompletion(0, user.getId(), id, LocalDateTime.now()));
            return new QuizResult(true, "Congratulations, you're right!");
        }
        return new QuizResult(false, "Wrong answer! Please, try again.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteQuiz(@PathVariable long id, @AuthenticationPrincipal AppUser user) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        if (user.getId() != quiz.getCreator().getId()) {
            throw new ResponseStatusException(FORBIDDEN);
        }
        quizRepository.delete(quiz);
    }
}
