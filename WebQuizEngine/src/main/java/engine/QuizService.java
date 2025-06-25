package engine;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    private final List<Quiz> quizList = new ArrayList<>();

    public Quiz add(ProposedQuiz proposed) {
        Quiz quiz = proposed.withId(quizList.size() + 1);
        quizList.add(quiz);
        return quiz;
    }

    public Optional<Quiz> getById(int id) {
        try {
            return Optional.of(quizList.get(id - 1));
        } catch (IndexOutOfBoundsException ignored) {
            return Optional.empty();
        }
    }

    public List<Quiz> getAll() {
        return quizList;
    }
}
