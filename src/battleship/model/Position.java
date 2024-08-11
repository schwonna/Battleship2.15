package battleship.model;

/**
 * Represents a position on the game grid with vertical and horizontal coordinates.
 * Coordinates are represented using integers.
 *
 */
public class Position {
    private int y;
    private int x;

    /**
     * Constructs a new Position with the specified horizontal and vertical coordinates.
     *
     * @param x Horizontal position.
     * @param y Vertical position.
     */
    Position(int y, int x) {
        this.y = y;
        this.x = x;
    }

    /**
     * Gets the horizontal position.
     *
     * @return The horizontal position.
     */
    public int getPosX(){return x;}

    /**
     * Gets the vertical position.
     *
     * @return The vertical position.
     */
    public int getPosY(){return y;}

}

