package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.BitSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

class BitSetMaze extends Maze {

    public static class FileFormatException extends Exception {}

    private final int height;
    private final int width;
    private final BitSet cells;

    BitSetMaze(int height, int width) {
        this.height = height;
        this.width = width;
        cells = new BitSet(height * width);
    }

    static BitSetMaze load(File file) throws FileFormatException, FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            int height = scanner.nextInt();
            int width = scanner.nextInt();
            BitSetMaze maze = new BitSetMaze(height, width);
            scanner.nextLine();
            for (int row = 0; row < height; row++) {
                String cells = scanner.nextLine();
                if (cells.length() != width) throw new FileFormatException();
                for (int col = 0; col < width; col++) {
                    switch (cells.charAt(col)) {
                        case '0' -> maze.unblock(row, col);
                        case '1' -> { /* cells are walls by default */ }
                        default -> throw new FileFormatException();
                    }
                }
            }
            return maze;
        } catch (NoSuchElementException e) {
            throw new FileFormatException();
        }
    }

    @Override
    int height() {
        return height;
    }

    @Override
    int width() {
        return width;
    }

    @Override
    boolean isPassage(int row, int col) {
        return cells.get(row * width + col);
    }

    void unblock(int row, int col) {
        cells.set(row * width + col);
    }
}
