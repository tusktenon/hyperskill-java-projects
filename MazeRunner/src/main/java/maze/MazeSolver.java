package maze;

import maze.Maze.Cell;

import java.util.*;

import static maze.Maze.MazeStructureException;

class MazeSolver {

    private final Maze maze;
    private Cell entrance;
    private Cell exit;
    private final Map<Cell, Cell> visited = new HashMap<>();

    private MazeSolver(Maze maze) {
        this.maze = maze;
    }

    static Set<Cell> solve(Maze maze) throws MazeStructureException {
        MazeSolver solver = new MazeSolver(maze);
        solver.findEntranceAndExit();
        solver.depthFirstSearch(solver.entrance, null);
        return solver.buildEscapePath();
    }

    private void findEntranceAndExit() throws MazeStructureException {
        List<Cell> exits = new ArrayList<>();

        for (int row = 1; row < maze.height() - 1; row++) {
            if (maze.isPassage(row, 0))
                exits.add(new Cell(row, 0));
            if (maze.isPassage(row, maze.width() - 1))
                exits.add(new Cell(row, maze.width() - 1));
        }

        for (int col = 1; col < maze.width() - 1; col++) {
            if (maze.isPassage(0, col))
                exits.add(new Cell(0, col));
            if (maze.isPassage(maze.height() - 1, col))
                exits.add(new Cell(maze.height() - 1, col));
        }

        if (exits.size() != 2) {
            throw new MazeStructureException(
                    "Maze should have exactly two entrances/exits; found " + exits.size());
        }
        entrance = exits.get(0);
        exit = exits.get(1);
    }

    private void depthFirstSearch(Cell current, Cell previous) {
        if (visited.containsKey(exit)) return;
        visited.put(current, previous);
        List<Cell> neighbors = new ArrayList<>(4);

        if (current.row() > 0 && maze.isPassage(current.row() - 1, current.col()))
            neighbors.add(new Cell(current.row() - 1, current.col()));
        if (current.row() < maze.height() - 1 && maze.isPassage(current.row() + 1, current.col()))
            neighbors.add(new Cell(current.row() + 1, current.col()));
        if (current.col() > 0 && maze.isPassage(current.row(), current.col() - 1))
            neighbors.add(new Cell(current.row(), current.col() - 1));
        if (current.col() < maze.width() - 1 && maze.isPassage(current.row(), current.col() + 1))
            neighbors.add(new Cell(current.row(), current.col() + 1));

        for (Cell neighbor : neighbors) {
            if (!visited.containsKey(neighbor))
                depthFirstSearch(neighbor, current);
        }
    }

    private Set<Cell> buildEscapePath() throws MazeStructureException {
        if (!visited.containsKey(exit))
            throw new MazeStructureException("Maze has no escape path.");

        Set<Cell> path = new HashSet<>();
        for (Cell cell = exit; cell != null; cell = visited.get(cell)) {
            path.add(cell);
        }
        return path;
    }
}
