package engine;

import jakarta.validation.constraints.*;

import java.util.Arrays;

public record ProposedQuiz(
        @NotBlank String title,
        @NotBlank String text,
        @NotNull @Size(min = 2) String[] options,
        int[] answer) {

    public ProposedQuiz {
        if (answer == null) answer = new int[0];
        Arrays.sort(answer);
    }

    public Quiz withId(int id) {
        return new Quiz(id, title, text, options, answer);
    }
}
