package engine.models;

import java.util.Arrays;

public record Solution(int[] answer) {

    public Solution {
        if (answer == null) answer = new int[0];
        Arrays.sort(answer);
    }

    public boolean solves(Quiz quiz) {
        return Arrays.equals(answer, quiz.getAnswer());
    }
}
