package battleship.model;

/**
 * Represents a single cell on the game grid.
 */
public class Cell {
    private Position position;
    private CellState cellState;

    /**
     * Constructs a new Cell with the specified vertical and horizontal coordinates.
     * Initializes the cell state to UNKNOWN.
     *
     * @param y Vertical position.
     * @param x Horizontal position.
     */
    Cell (int y, int x) {
        this.position = new Position(y, x);
        this.cellState = CellState.UNKNOWN;
    }

    /**
     * Gets the position of the cell.
     *
     * @return The position of the cell.
     */
    public Position getPosition(){return position;}

    /**
     * Gets the state of the cell.
     *
     * @return The state of the cell.
     */
    public CellState getCellState() {
        return cellState;
    }

    /**
     * Sets the state of the cell.
     *
     * @param cellState The new state of the cell.
     */
    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

}

