package maze;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Please, enter the size of a maze");
            Maze maze = MazeGenerator.generate(in.nextInt(), in.nextInt());
            System.out.println(maze);
        }
    }
}
