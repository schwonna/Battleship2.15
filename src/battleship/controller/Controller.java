package battleship.controller;

import battleship.model.Model;
import battleship.view.IView;

/**
 * The controller class for the Battleship game, coordinating interactions between the model and the view.
 *
 * This controller handles user inputs and updates the game state accordingly. It uses a {@link Model}
 * to manage the game logic and a {@link IView} to display the game state.
 *
 */
public class Controller implements IController{
    private Model model;
    private IView view;
    private GameState state;

    /**
     * Creates a new controller instance with the initial game state set to {@link GameState}.
     */
    public Controller() {this.state = GameState.START;}

    /**
     * Sets the model to be used by this controller.
     *
     * @param model the model to use
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Sets the view to be used by this controller.
     *
     * @param view the view to use
     */
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Gets the current game state.
     *
     * @return the current game state
     */
    public GameState getGameState(){return this.state;}

    /**
     * Calls the draw methods of the view, depending on the current game state.
     */
    public void nextFrame(){
        switch (state) {
            case START -> {
                view.drawStartScreen();
            }
            case PLACE_SHIPS -> {
                if(!model.allShipsSet(model.getCurrentShipIndex())){
                    view.drawBuildScreen(model.getInvPlayerShipsOnCompGrid(), model.getDirection());
                    view.drawShipsBuild(model.getShipLengths());
                }
                if (model.getCurrentShipIndex() >= model.getShipLengths().length) {
                    state = GameState.PLAYING;
                } else{
                    state = GameState.PLACE_SHIPS;
                }
            }
            case PLAYING -> {
                if(model.allShipsSet(model.getCurrentShipIndex())){
                    view.drawPlayScreen(model.getPlayerGrid(), model.getComputerGrid(),
                            model.getInvPlayerShipsOnCompGrid(), model.getShipLengths());
                }

                if(model.areAllShipsSunk(model.getPlayerShips())){
                    view.drawGameOverScreenPlayerLoose();
                    state = GameState.GAME_OVER;
                } else if(model.areAllShipsSunk(model.getCompShips())){
                    view.drawGameOverScreenPlayerWin();
                    state = GameState.GAME_OVER;
                }

            }
            case GAME_OVER -> {
                model.checkWinCondition(model.getPlayer());
            }
        }
    }

    /**
     * Handles keyboard input based on the current game state.
     *
     * @param keyPressed the key pressed by the user
     */
    public void handleKeyboardInput(char keyPressed){
        switch(state){
            case START -> {
                if(keyPressed == ' ') {
                    model.startShipPlacement();
                    state = GameState.PLACE_SHIPS;
                }
            }
            case PLACE_SHIPS -> {
                // User feedback for Input
                System.out.println("Current direction: " + model.getDirection());

                // vertical arrow (UP/ DOWN)
                if(keyPressed == 'v'){
                  model.setVerticalDirection();
                }

                // horizontal arrow (LEFT/ RIGHT)
                if(keyPressed == 'h'){
                  model.setHorizontalDirection();
                } else {
                    System.out.println("This Keyboard Input has no effect.");
                }
            }
            case PLAYING, GAME_OVER -> {
                System.out.println("This Keyboard Input has no effect.");

            }
            default -> throw new IllegalStateException("Unexpected GameState: " + state);
        }
    }

    /**
     * Handles mouse input based on the current game state.
     *
     * @param row the row clicked on the grid
     * @param col the column clicked on the grid
     */
    public void handleMouseInput(int row, int col) {
        // converting mouse coordinates into used data types
        char charRow = (char) (model.getComputerGrid().getAbc()[0] + row);
        int newCol = col +1;

        // User feedback for Input
        System.out.println("(Player turn) Clicked Field: " + charRow + " " + newCol);

        if(state == GameState.PLACE_SHIPS){
            if(!model.allShipsSet(model.getCurrentShipIndex())){
                model.buildShip(charRow, newCol);
            }

        }
        if(state == GameState.PLAYING){
            if (model.playershoot(charRow, newCol)) {
                model.computerShoot();
            }
        }
    }


}
