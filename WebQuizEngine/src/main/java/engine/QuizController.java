package engine;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private static final Quiz quiz = new Quiz(
            "The Java Logo",
            "What is depicted on the Java logo?",
            new String[]{"Robot", "Tea leaf", "Cup of coffee", "Bug"},
            2);

    private static final QuizResult correct =
            new QuizResult(true, "Congratulations, you're right!");

    private static final QuizResult wrong =
            new QuizResult(false, "Wrong answer! Please, try again.");

    @GetMapping
    public Quiz getQuiz() {
        return quiz;
    }

    @PostMapping
    public QuizResult answerQuiz(@RequestParam int answer) {
        return answer == quiz.answer() ? correct : wrong;
    }
}
