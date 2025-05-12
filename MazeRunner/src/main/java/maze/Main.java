package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private final Scanner in;
    private Maze maze;

    private Main(Scanner in) {
        this.in = in;
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            new Main(in).run();
        }
    }

    void run() {
        while (true) {
            displayMenu();
            switch (in.nextLine()) {
                case "1" -> generateMaze();
                case "2" -> loadMaze();
                case "3" -> saveMaze();
                case "4" -> displayMaze();
                case "5" -> displayEscape();
                case "0" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("""
                
                === Menu ===
                1. Generate a new maze
                2. Load a maze""");

        if (maze != null) {
            System.out.println("""
                    3. Save the maze
                    4. Display the maze
                    5. Find the escape""");
        }
        System.out.println("0. Exit");
    }

    private void generateMaze() {
        System.out.println("Enter the size of a new maze");
        try {
            int size = Integer.parseInt(in.nextLine());
            if (size >= 3) {
                maze = MazeGenerator.generate(size, size);
                System.out.println(maze);
            } else {
                System.out.println("Maze size must be at least 3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Maze dimensions must be positive numbers.");
        }
    }

    private void loadMaze() {
        String fileName = in.nextLine();
        try {
            maze = BitSetMaze.load(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist.%n", fileName);
        } catch (BitSetMaze.FileFormatException e) {
            System.out.println("Cannot load maze: invalid file format.");
        }
    }

    private void saveMaze() {
        if (maze == null) {
            System.out.println("Option unavailable. Please generate or load a maze.");
        } else {
            String fileName = in.nextLine();
            try {
                maze.save(new File(fileName));
            } catch (FileNotFoundException ignored) {
                System.out.printf("Cannot save maze to file %s.%n", fileName);
            }
        }
    }

    private void displayMaze() {
        System.out.println(
                maze == null ? "Option unavailable. Please generate or load a maze." : maze
        );
    }

    private void displayEscape() {
        if (maze == null) {
            System.out.println("Option unavailable. Please generate or load a maze.");
        } else {
            try {
                System.out.println(maze.showEscapePath());
            } catch (Maze.MazeStructureException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
