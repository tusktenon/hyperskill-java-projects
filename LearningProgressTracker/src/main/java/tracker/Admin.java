package tracker;

import java.util.Scanner;
import java.util.regex.Pattern;

class Admin {

    static final Pattern VALID_EMAIL = Pattern.compile("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");
    static final Pattern VALID_NAME = Pattern.compile("([A-Za-z][-']?)+[A-Za-z]");

    private int students = 0;
    private final Scanner in;

    Admin(Scanner in) {
        this.in = in;
    }

    void run() {
        System.out.println("Learning Progress Tracker");

        while (true) {
            switch (in.nextLine().trim().toLowerCase()) {
                case "add students" -> addStudents();
                case "back" -> System.out.println("Enter 'exit' to exit the program.");
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
                case "" -> System.out.println("No input.");
                default -> System.out.println("Unknown command!");
            }
        }
    }

    private void addStudents() {
        System.out.println("Enter student credentials or 'back' to return:");

        while (true) {
            String input = in.nextLine();
            if ("back".equalsIgnoreCase(input.trim())) {
                System.out.println("Total " + students + " students have been added.");
                return;
            }
            if (validateInput(input)) {
                students++;
                System.out.println("The student has been added.");
            }
        }
    }

    private static boolean validateInput(String input) {
        String[] tokens = input.split("\\s+");
        if (tokens.length < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        }
        if (!VALID_NAME.matcher(tokens[0]).matches()) {
            System.out.println("Incorrect first name.");
            return false;
        }
        for (int i = 1; i < tokens.length - 1; i++) {
            if (!VALID_NAME.matcher(tokens[i]).matches()) {
                System.out.println("Incorrect last name.");
                return false;
            }
        }
        if (!VALID_EMAIL.matcher(tokens[tokens.length - 1]).matches()) {
            System.out.println("Incorrect email.");
            return false;
        }
        return true;
    }
}
