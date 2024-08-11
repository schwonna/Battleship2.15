package battleship.model;

/**
 * Represents the data structure for managing game-related information in the Battleship game.
 * It includes player and computer grids, ships, and ship lengths.
 */

public class Data {

    /**
     * The grid representing the player shots on the computer grid.
     */
    Grid playerGrid;

    /**
     * The grid where the computer shoots at. It represents the player ships and their status.
     */
    Grid invPlayerShipsOnGrid;

    /**
     * The grid representing the computer shots on the player grid.
     */
    Grid computerGrid;

    /**
     * The grid where the player shoots at. It represents the computer ships and their status.
     */
    Grid invCompGrid;

    /**
     * An array of ships controlled by the computer.
     */
    Ship[] computerShips;

    /**
     * An array of ships controlled by the player.
     */
    Ship[] playerShips;

    /**
     * An array representing the lengths of the ships in the game.
     */
    int[] shipLengths = {5, 4, 3, 3, 2, 2};
}
