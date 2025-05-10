package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

abstract class Maze {

    public static final String PASSAGE_STRING = "  ";
    public static final String WALL_STRING = "██";

    abstract int height();

    abstract int width();

    abstract boolean isPassage(int row, int col);

    boolean isWall(int row, int col) {
        return !isPassage(row, col);
    }

    final void save(File file) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.printf("%d %d%n", height(), width());
            for (int row = 0; row < height(); row++) {
                for (int col = 0; col < width(); col++) {
                    writer.print(isPassage(row, col) ? 0 : 1);
                }
                writer.println();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(height() * (WALL_STRING.length() * width() + 1));
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                builder.append(isPassage(row, col) ? PASSAGE_STRING : WALL_STRING);
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
