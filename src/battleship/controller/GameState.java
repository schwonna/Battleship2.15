package battleship.controller;

/**
 * All possible game states. This class is used to implement a state machine in the controller.
 */
public enum GameState {
    /**
     * The starting state of the game. Transitions to the <code>PLACE_SHIPS</code> state.
     */
    START,

    /**
     * The state where the user can set the ships on the grid. Transitions to the <code>PLAYING</code> state.
     */
    PLACE_SHIPS,

    /**
     * The state in which the user can play Battleship. Transitions to the <code>GAME_OVER</code> state.
     */
    PLAYING,

    /**
     * The end state of the game, when Player or Computer has won.
     */
    GAME_OVER
}
