package battleship.view;

import battleship.model.Grid;


/**
 * Interface defining the necessary methods for a view to be used by the controller in Battleship.
 */
public interface IView {

    /**
     * Draws the start screen of the game.
     */
    void drawStartScreen();

    /**
     * Draws the ship placement screen with the provided computer grid and direction indicator.
     *
     * @param compGrid   The computer grid to display (contains player ships).
     * @param direction  The direction indicator (0 for horizontal, 1 for vertical).
     */
    void drawBuildScreen(Grid compGrid, int direction);

    /**
     * Draws the ship placement screen with the lengths of the ships to be built.
     *
     * @param shipLengths  An array containing the lengths of the ships to be built.
     */
    void drawShipsBuild(int[] shipLengths);

    /**
     * Draws the main play screen with player and computer grids, as well as the invisible player ships on the computer grid.
     *
     * @param playerGrid               The player's grid: player shoot at computer ships on it.
     * @param computerGrid             The computer's grid: computer shoot at player ships on it.
     * @param invPlayerShipsOnCompGrid The invisible player ships on the computer grid.
     * @param shipLengths              An array containing the lengths of the ships.
     */
    void drawPlayScreen(Grid playerGrid, Grid computerGrid, Grid invPlayerShipsOnCompGrid, int[] shipLengths);

    /**
     * Draws the game over screen for the player's victory.
     */
    void drawGameOverScreenPlayerWin();

    /**
     * Draws the game over screen for the computer's victory.
     */
    void drawGameOverScreenPlayerLoose();

}








