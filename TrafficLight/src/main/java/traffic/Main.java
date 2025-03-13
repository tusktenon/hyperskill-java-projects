package traffic;

public class Main {
    private static final String greeting = "Welcome to the traffic management system!";
    private static final String menu = """
            Menu:
            1. Add
            2. Delete
            3. System
            0. Quit
            """;

    public static void main(String[] args) {
        System.out.println(greeting);
        System.out.println(menu);
    }
}
