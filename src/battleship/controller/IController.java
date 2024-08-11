package battleship.controller;

/**
 * The interface representing the controller from the perspective of a view object.
 * All elements not included in this interface are invisible to the view.
 */
public interface IController {

    /**
     * Retrieves the current game state from the controller.
     *
     * @return The current game state.
     */
    GameState getGameState();

    /**
     * Called to determine what the view should display.
     * The controller calls one of the draw()-methods from the view.
     */
    void nextFrame();

    /**
     * Called whenever the user presses a key on the keyboard.
     *
     * @param keyPressed The key that was pressed.
     */
    void handleKeyboardInput(char keyPressed);

    /**
     * Called whenever the user clicks on the game window.
     *
     * @param row The selected y-coordinate.
     * @param col The selected x-coordinate.
     */
    void handleMouseInput(int row, int col);


}

