package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates a maze using a slightly modified version of the randomized Prim's algorithm
 * <a href="https://www.youtube.com/watch?v=cQVH4gcb3O4">shown here</a>.
 */
class MazeGenerator {

    private record Cell(int row, int col) {}

    private record FrontierCell(int row, int col, int adjacentRow, int adjacentCol) {}

    final BitSetMaze maze;
    final Random rng = new Random();
    final List<FrontierCell> frontierCells = new ArrayList<>();
    final List<Cell> possibleExits = new ArrayList<>();
    final int height;
    final int width;

    static Maze generate(int height, int width) {
        MazeGenerator generator = new MazeGenerator(height, width);
        generator.build();
        return generator.maze;
    }

    private MazeGenerator(int height, int width) {
        maze = new BitSetMaze(height, width);
        // This algorithm only works for mazes with odd dimensions.
        // If a requested dimension is even, we decrease it by one during maze generation
        // and extend the maze exits afterward as needed.
        this.height = 2 * ((height - 1) / 2) + 1;
        this.width = 2 * ((width - 1) / 2) + 1;
    }

    private void build() {
        selectStarterCell();
        addPassages();
        addExits();
    }

    private void selectStarterCell() {
        // This algorithm must start at a cell with odd coordinates
        // that is not on the edge of the maze
        int row = 2 * rng.nextInt(height / 2) + 1;
        int col = 2 * rng.nextInt(width / 2) + 1;
        frontierCells.add(new FrontierCell(row, col, row, col));
    }

    private void addPassages() {
        while (!frontierCells.isEmpty()) {
            FrontierCell current = frontierCells.remove(rng.nextInt(frontierCells.size()));
            if (maze.isWall(current.row, current.col)) {
                maze.unblock(current.row, current.col);
                maze.unblock(current.adjacentRow, current.adjacentCol);
                addFrontierCellsAndPossibleExits(current.row, current.col);
            }
        }
    }

    private void addExits() {
        for (int exits = 0; exits < 2; exits++) {
            Cell exit = possibleExits.remove(rng.nextInt(possibleExits.size()));
            maze.unblock(exit.row, exit.col);
            if (height < maze.height() && exit.row == height - 1)
                maze.unblock(height, exit.col);
            else if (width < maze.width() && exit.col == width - 1)
                maze.unblock(exit.row, width);
        }
    }

    private void addFrontierCellsAndPossibleExits(int row, int col) {
        if (row == 1)
            possibleExits.add(new Cell(0, col));
        else if (maze.isWall(row - 2, col))
            frontierCells.add(new FrontierCell(row - 2, col, row - 1, col));

        if (row == height - 2)
            possibleExits.add(new Cell(height - 1, col));
        else if (maze.isWall(row + 2, col))
            frontierCells.add(new FrontierCell(row + 2, col, row + 1, col));

        if (col == 1)
            possibleExits.add(new Cell(row, 0));
        else if (maze.isWall(row, col - 2))
            frontierCells.add(new FrontierCell(row, col - 2, row, col - 1));

        if (col == width - 2)
            possibleExits.add(new Cell(row, width - 1));
        else if (maze.isWall(row, col + 2))
            frontierCells.add(new FrontierCell(row, col + 2, row, col + 1));
    }
}
