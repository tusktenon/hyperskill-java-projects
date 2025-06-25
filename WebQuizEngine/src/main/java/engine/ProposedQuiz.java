package engine;

public record ProposedQuiz(String title, String text, String[] options, int answer) {

    public Quiz withId(int id) {
        return new Quiz(id, title, text, options, answer);
    }
}
