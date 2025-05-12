package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.function.BiPredicate;

abstract class Maze {

    record Cell(int row, int col) {}

    public static class FileFormatException extends Exception {}

    public static class MazeStructureException extends Exception {
        public MazeStructureException(String message) {
            super(message);
        }
    }

    static final String PASSAGE_STRING = "  ";
    static final String WALL_STRING = "██";
    static final String PATH_STRING = "//";

    private Set<Cell> escapePath;
    private MazeStructureException escapePathException;

    abstract int height();

    abstract int width();

    abstract boolean isPassage(int row, int col);

    boolean isWall(int row, int col) {
        return !isPassage(row, col);
    }

    public final void save(File file) throws FileNotFoundException {
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
        return toString((row, path) -> false);
    }

    public String showEscapePath() throws MazeStructureException {
        if (escapePathException != null) {
            throw escapePathException;
        }
        if (escapePath == null) {
            try {
                escapePath = MazeSolver.solve(this);
            } catch (MazeStructureException e) {
                escapePathException = e;
                throw e;
            }
        }
        return toString((row, col) -> escapePath.contains(new Cell(row, col)));
    }

    private String toString(BiPredicate<Integer, Integer> isOnPath) {
        StringBuilder builder = new StringBuilder(height() * (WALL_STRING.length() * width() + 1));
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                builder.append(
                        isOnPath.test(row, col)
                                ? PATH_STRING
                                : isPassage(row, col)
                                ? PASSAGE_STRING
                                : WALL_STRING
                );
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
