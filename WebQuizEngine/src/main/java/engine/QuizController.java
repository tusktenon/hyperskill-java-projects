package engine;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private static final QuizResult correct =
            new QuizResult(true, "Congratulations, you're right!");

    private static final QuizResult wrong =
            new QuizResult(false, "Wrong answer! Please, try again.");

    private final QuizRepository quizRepository;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping
    public List<Quiz> getAll() {
        List<Quiz> quizzes = new ArrayList<>((int) quizRepository.count());
        quizRepository.findAll().forEach(quizzes::add);
        return quizzes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable long id) {
        return ResponseEntity.of(quizRepository.findById(id));
    }

    @PostMapping
    public Quiz add(@Valid @RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<QuizResult> answerQuiz(@PathVariable long id,
                                                 @RequestBody Solution solution) {
        return ResponseEntity.of(
                quizRepository.findById(id).map(quiz -> solution.solves(quiz) ? correct : wrong));
    }
}
