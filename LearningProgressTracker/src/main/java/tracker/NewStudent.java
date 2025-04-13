package tracker;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

record NewStudent(String email, String fullName) {

    static final Pattern VALID_EMAIL = Pattern.compile("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");
    static final Pattern VALID_NAME = Pattern.compile("([A-Za-z][-']?)+[A-Za-z]");

    static NewStudent parse(String input) throws Exception {
        String[] tokens = input.split("\\s+");
        if (tokens.length < 3)
            throw new Exception("Incorrect credentials.");
        if (!VALID_NAME.matcher(tokens[0]).matches())
            throw new Exception("Incorrect first name.");
        for (int i = 1; i < tokens.length - 1; i++) {
            if (!VALID_NAME.matcher(tokens[i]).matches())
                throw new Exception("Incorrect last name.");
        }
        String email = tokens[tokens.length - 1];
        if (!VALID_EMAIL.matcher(email).matches())
            throw new Exception("Incorrect email.");

        String fullName = Arrays.stream(tokens)
                .limit(tokens.length - 1)
                .collect(Collectors.joining(" "));
        return new NewStudent(email, fullName);
    }
}
