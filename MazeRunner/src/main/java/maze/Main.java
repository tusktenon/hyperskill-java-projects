package maze;

public class Main {

    public static final byte[][] MAZE = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };

    public static final String PASSAGE = "  ";
    public static final String WALL = "██";

    public static void main(String[] args) {
        for (var row : MAZE) {
            for (var cell : row) {
                System.out.print(cell == 1 ? WALL : PASSAGE);
            }
            System.out.println();
        }
    }
}
