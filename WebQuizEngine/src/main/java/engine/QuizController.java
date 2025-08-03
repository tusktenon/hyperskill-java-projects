package engine;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private static final QuizResult correct =
            new QuizResult(true, "Congratulations, you're right!");

    private static final QuizResult wrong =
            new QuizResult(false, "Wrong answer! Please, try again.");

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quiz> getAll() {
        return quizService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable int id) {
        return ResponseEntity.of(quizService.getById(id));
    }

    @PostMapping
    public Quiz add(@Valid @RequestBody ProposedQuiz proposed) {
        return quizService.add(proposed);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<QuizResult> answerQuiz(@PathVariable int id,
                                                 @RequestBody Solution solution) {
        return ResponseEntity.of(
                quizService.getById(id).map(quiz -> solution.solves(quiz) ? correct : wrong));
    }
}
