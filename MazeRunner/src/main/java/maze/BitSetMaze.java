package maze;

import java.util.BitSet;

class BitSetMaze extends Maze {

    private final int height;
    private final int width;
    private final BitSet cells;

    BitSetMaze(int height, int width) {
        this.height = height;
        this.width = width;
        cells = new BitSet(height * width);
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
