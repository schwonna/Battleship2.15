package battleship.model;

/**
 * Represents a ship in the Battleship game, consisting of multiple cells.
 * Provides methods to retrieve the length and cells of the ship.
 */
public class Ship {
    private Cell[] fields;

    /**
     * Constructs a new ship with the specified cells.
     *
     * @param fields The cells that make up the ship.
     */
    Ship(Cell[] fields) {
        this.fields = fields;
    }

    /**
     * Gets the length of the ship.
     *
     * @return The length of the ship.
     */
    public int getLength(){return fields.length;}

    /**
     * Sets the cells of the ship.
     *
     * @param shipCells The cells that make up the ship.
     */
    public void setCells(Cell[] shipCells) {this.fields = shipCells;}

    /**
     * Gets the cells that make up the ship.
     *
     * @return The cells that make up the ship.
     */
    public Cell[] getCells() {return this.fields;}

}
