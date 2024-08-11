package battleship.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game grid with cells arranged in rows and columns.
 * Provides methods to access cells and retrieve the grid position.
 */

public class Grid {
    private final static char[] ABC = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private final Map<Character, Cell[]> GRID = new HashMap<>();
    private Position position;

    /**
     * Constructs a new grid with the specified number of rows and columns.
     *
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     */
    Grid(int rows, int cols) {
        this.position = new Position(rows, cols);

        for (int y = 0; y < rows; y++) {
            Cell[] fields = new Cell[rows];
            for (int x = 0; x < cols; x++) {
                fields[x] = new Cell(y,x);
            }
            GRID.put(ABC[y], fields);
        }
    }

    /**
     * Gets the cell at the specified position in the grid.
     *
     * @param y The vertical position.
     * @param x The horizontal position.
     * @return The cell at the specified position.
     */
    public Cell getField(char y, int x) {return GRID.get(y)[x];}

    /**
     * Gets the position of the grid.
     *
     * @return The position of the grid.
     */
    public Position getPosition() {return position;}

    /**
     * Gets the array of characters representing the vertical positions on the grid.
     *
     * @return The array of characters representing vertical positions.
     */
    public char[] getAbc(){return ABC;}

    /**
     * Converts the grid to a string for display purposes.
     *
     * @return A string representation of the grid.
     */
    @Override
    public String toString() {
        StringBuilder gridString = new StringBuilder();
        gridString.append("  "); // Header 2 Whitespace

        // numbers horizontal
        for (int x = 0; x < position.getPosX(); x++) {
            gridString.append(x + 1).append(" ");
        }
        gridString.append("\n");

        // letters vertical
        for (int y = 0; y < position.getPosY(); y++) {
            Cell[] fields = GRID.get(ABC[y]);
            gridString.append(ABC[y]).append(" ");
            for (Cell cell : fields) {
                gridString.append(cell.getCellState().getFieldChar());
                gridString.append(" ");
            }
            gridString.append("\n");
        }
        return gridString.toString();
    }
}

