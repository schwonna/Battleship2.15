package battleship.model;

/**
 * Represents the possible states of a cell on the game grid.
 * Each state is associated with a character for display purposes.
 *
 */
public enum CellState {
    WATER       ('W'),
    SHIP       ('S'),
    HIT        ('H'),
    DESTROYED  ('D'),
    UNKNOWN    (' ');
    private final char fieldChar;

    /**
     * Constructs a new CellState with the associated character.
     *
     * @param fieldChar The character associated with the cell state.
     */
    CellState(char fieldChar) {this.fieldChar = fieldChar;}

    /**
     * Gets the character associated with the cell state.
     *
     * @return The character associated with the cell state.
     */
    public char getFieldChar() {
        return fieldChar;
    }
}
