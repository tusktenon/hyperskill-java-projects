package maze;

abstract class Maze {

    public static final String PASSAGE_STRING = "  ";
    public static final String WALL_STRING = "██";

    abstract int height();

    abstract int width();

    abstract boolean isPassage(int row, int col);

    boolean isWall(int row, int col) {
        return !isPassage(row, col);
    }

    @Override
    public String toString() {
        StringBuilder builder =
                new StringBuilder(WALL_STRING.length() * height() * width() + height());
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                builder.append(isPassage(row, col) ? PASSAGE_STRING : WALL_STRING);
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
