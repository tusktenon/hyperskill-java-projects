package engine;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private static final QuizResult correct =
            new QuizResult(true, "Congratulations, you're right!");

    private static final QuizResult wrong =
            new QuizResult(false, "Wrong answer! Please, try again.");

    private final QuizRepository quizRepository;
    private final AppUserRepository userRepository;

    public QuizController(QuizRepository quizRepository, AppUserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable long id) {
        return ResponseEntity.of(quizRepository.findById(id));
    }

    @PostMapping
    public Quiz add(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal AppUser creator) {
        quiz.setCreator(creator);
        return quizRepository.save(quiz);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<QuizResult> answerQuiz(@PathVariable long id,
                                                 @RequestBody Solution solution) {
        return ResponseEntity.of(
                quizRepository.findById(id).map(quiz -> solution.solves(quiz) ? correct : wrong));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable long id,
                                        @AuthenticationPrincipal AppUser user) {
        return quizRepository.findById(id)
                .map(quiz -> {
                    if (user.getId() == quiz.getCreator().getId()) {
                        quizRepository.delete(quiz);
                        return new ResponseEntity<>(NO_CONTENT);
                    }
                    return new ResponseEntity<>(FORBIDDEN);
                })
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }
}
