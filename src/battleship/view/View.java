package battleship.view;

import battleship.controller.GameState;
import battleship.controller.IController;
import battleship.model.Grid;
import battleship.model.Cell;
import battleship.model.CellState;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import gifAnimation.Gif;

/**
 * The View represents the graphical user interface for the Battleship game.
 * It extends the PApplet class from the Processing library.
 * This class is responsible for rendering the game screens, including the start screen,
 * game over screens, and the main play screen with player and computer grids.
 *
 */
public class View extends PApplet implements IView {
    private IController controller;
    private final int WIDTH, HEIGHT;
    private PImage startScreen;
    private Gif shootingShip;

    /**
     * Constructs a View object with the specified width and height.
     *
     * @param WIDTH  The width of the application window.
     * @param HEIGHT The height of the application window.
     */
    public View(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    /**
     * Sets the controller for the View.
     *
     * @param controller The controller to set.
     */
    public void setController(IController controller) {this.controller = controller;}

    /**
     * Sets up the initial settings for the application window.
     */
    @Override
    public void settings() {
        setSize(WIDTH, HEIGHT);
        pixelDensity(2);
    }

    /**
     * Initializes the View by setting the application window title.
     */
    @Override
    public void setup() {
        surface.setTitle("Battleship");

        //
        GifThread thread1 = new GifThread(this); // gesamter View wird an parent im Thread übergeben
        thread1.start();
        try {
            thread1.join(); // join: warten bis der thread durchgelaufen ist
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the GIF image and start screen image during the setup process.
     */
    public void firstLoadImages(){
        startScreen = loadImage("startscreen.png");
        shootingShip = new Gif(this, "shootingShip.gif");
        shootingShip.play(); // aktiviert das GIF
    }

    /**
     * To draw the GIF.
     */
    private void drawShootingGIF(){
        image(shootingShip, width/2 +350, 25, 200, 200);
    }

    /**
     * Draws the current frame based on the game state.
     */
    @Override
    public void draw() {controller.nextFrame();}

    /**
     * Draws the start screen.
     */
    public void drawStartScreen(){
        background(255);
        imageMode(CORNER);
        image(startScreen, 0, 0, width, height);
    }

    /**
     * Draws the game over screen for the player's victory.
     */
    public void drawGameOverScreenPlayerWin(){
        fill(0,200,0);
        textSize(25);
        text("Game Over!", (float) width/2 +50, 80);
        text("YOU WIN!", (float) width/2+80, 105);
    }

    /**
     * Draws the game over screen for the computer's victory.
     */
    public void drawGameOverScreenPlayerLoose(){
        fill(0,200,0);
        textSize(25);
        text("Game Over!", (float) width/2 +50, 80);
        text("COMPUTER WIN.", (float) width/2+80, 105);
    }

    /**
     * Draws the screen for placing player ships.
     *
     * @param compGrid The grid where the player place ships.
     * @param direction The direction of the ship to place (vertical, horizontal).
     */
    public void drawBuildScreen(Grid compGrid, int direction){
        background(255);
        fill(0);
        drawGuideBuild();
        int cellSize = 50;

        for (int row = 0; row < compGrid.getPosition().getPosY(); row++) {
            for (int col = 0; col < compGrid.getPosition().getPosX(); col++) {

                // Position of the clicked cell (row, col)
                Cell cell = compGrid.getField(compGrid.getAbc()[row], col);

                // Checks cellstate and draw cell depending on its state
                if (cell.getCellState() == CellState.UNKNOWN) {
                    fill(200);
                } else if (cell.getCellState() == CellState.WATER) {
                    fill(0, 0, 255);
                } else if (cell.getCellState() == CellState.SHIP) {
                    fill(165,42,42);
                } else if (cell.getCellState() == CellState.HIT) {
                    fill(255, 0, 0);
                }

                // Draw cell on current position (row, col)
                rect(col * cellSize + width/2 -200, row * cellSize + height/2 -200, cellSize, cellSize);
            }
        }
        textSize(15);
        fill(0);
        text("First you have to place your Ships on the grid:", width/2-150, height/2 - 320);

        // Ship direction
        if ((direction == 0)) {
            drawHorizontalPlacementDirection();
        } else {
            drawVerticalPlacementDirection();
        }

    }

    /**
     * User feedback when direction of current ship to place is horizontal.
     */
    public void drawHorizontalPlacementDirection(){
        noFill();
        stroke(0);
        rect(50, 680, 210, 50);
        text("Selected direction: HORIZONTAL", 55, 710);
    }

    /**
     * User feedback when direction of current ship to place is vertical.
     */
    public void drawVerticalPlacementDirection(){
        noFill();
        stroke(0);
        rect(50, 680, 210, 50);
        text("Selected direction: VERTICAL", 55, 710);
    }

    /**
     * Draws an information for the player which ship lengths are available and the order of the lengths for the placement.
     *
     * @param shipLengths All sizes of ships.
     */
    public void drawShipsBuild(int[] shipLengths) {
        int xOffset = 50; // start position x
        int yOffset = 250; // start position y
        int cellSize = 40;

        text("These are your ships:", xOffset,yOffset - 15);

        for (int i = 0; i < shipLengths.length; i++) {
            int length = shipLengths[i];

            // Width of the rectangle based on the length of the ship
            int rectWidth = length * cellSize;

            fill(0);
            text("ship size: " + length, xOffset, yOffset + 15);
            fill(255, 255, 0);
            rect(xOffset, yOffset + 23, rectWidth, cellSize);

            // updates the starting position for the next ship (with a gap in between)
            yOffset += cellSize + 25;
        }
    }
    private void drawGuideBuild(){
        fill(166,233,166);
        rect(20,10, 345,170);

        fill(0);
        textSize(12);
        text("Guide for ship-placement: \n\n " +
                        "1) Choose the direction: \npress LEFT or RIGHT for horizontal placement\n" +
                        " press UP or DOWN for vertical placement \n\n Tipp: the actual chosen direction is displayed on bottom left. \n\n" +
                        "2) Click via mouse on the grid to select ships start position. \n " +
                        "If the clicked field is yellow you can´t place the ship on this field, \n" +
                        "because of the ship length. In this case choose another field. "
                ,30,30);
    }


    /**
     * Draws the main play screen with player and computer grids.
     * There are a legend to describe the different field colours of the grids,
     * a guide with informations and an information about all possible ship lengths.
     * Also there is a little GIF.
     *
     * @param p The player grid with the computer ships (player has to shoot at this).
     * @param c The computer grid with the player ships (computer has to shoot at this).
     * @param invC The invisible computer grid with the computer ships (player cant see computer ships).
     * @param shipL All available ship lengths.
     */
    public void drawPlayScreen(Grid p, Grid c, Grid invC, int[] shipL){
        background(255);
        drawShootingGIF();

        // Tools
        drawGuidePlaying();
        drawLegend();
        drawShipsLenPlayScreen(shipL);

        // Game Essentials
        drawPlayerGrid(p);
        drawComputerGrid(c);
        drawInvPlayerShipsOnGrid(invC);
    }
    private void drawGuidePlaying(){
        fill(166,233,166);
        rect(20,10, 385,120);

        fill(0);
        textSize(12);
        text("Guide for battle: \n\n " +
                        "Shoot: MOUSECLICK on the left field to shoot at the enemy. " +
                        "\n The Legend on the right bottom shows the meaning of the field colours.\n" +
                        " Player and enemy take turns after each shot."
                ,30,30);
    }

    private void drawLegend(){
        textSize(15);
        fill(0);
        text("Legend:", 700, 650);

        textSize(15);
        fill(200);
        text("UNKNOWN field", 700, 670);

        textSize(15);
        fill(0,0,255);
        text("WATER field", 700, 690);

        textSize(15);
        fill(165,42,42);
        text("SHIP field", 700, 710);

        textSize(15);
        fill(255,127,36);
        text("Enemy HIT your ship", 700, 730);

        textSize(15);
        fill(255,0,0);
        text("DESTROYED ship", 700, 750);
    }

    private void drawShipsLenPlayScreen(int[] shipsLen) {
        int xOffset = 450;
        int yOffset = 50;

        text("All ship lengths:", xOffset,yOffset - 25);

        for (int i = 0; i < shipsLen.length; i++) {
            int length = shipsLen[i];

            fill(0);
            text("ship size: " + length, xOffset, yOffset);

            // updates the starting position for the next ship (with a gap in between)
            yOffset += 20;
        }
    }
    private void drawPlayerGrid(Grid playerGrid) {
        int cellSize = 50;

        for (int row = 0; row < playerGrid.getPosition().getPosY(); row++) {
            for (int col = 0; col < playerGrid.getPosition().getPosX(); col++) {
                // Cell at the current position (row, col)
                Cell cell = playerGrid.getField(playerGrid.getAbc()[row], col);

                // Check the status of the cell and draw accordingly
                if (cell.getCellState() == CellState.UNKNOWN) {
                    fill(200);
                } else if (cell.getCellState() == CellState.WATER) {
                    fill(0, 0, 255);
                } else if (cell.getCellState() == CellState.HIT) {
                    fill(165,42,42);
                } else if (cell.getCellState() == CellState.DESTROYED) {
                    fill(255, 0, 0);
                }

                // Draw cell at the current position (row, col)
                rect(col * cellSize +100, row * cellSize +200, cellSize, cellSize);
            }
        }
        textSize(15);
        fill(0);
        text("Enemy Ships:", 100, 180);
    }

    private void drawComputerGrid(Grid computerGrid) {
        int cellSize = 40;

        for (int row = 0; row < computerGrid.getPosition().getPosY(); row++) {
            for (int col = 0; col < computerGrid.getPosition().getPosX(); col++) {
                // Cell at the current position (row, col)
                Cell cell = computerGrid.getField(computerGrid.getAbc()[row], col);

                // Check the status of the cell and draw accordingly
                if (cell.getCellState() == CellState.UNKNOWN) {
                    fill(200);
                } else if (cell.getCellState() == CellState.WATER) {
                    fill(0, 0, 255);
                } else if (cell.getCellState() == CellState.SHIP) {
                    noFill();
                } else if (cell.getCellState() == CellState.HIT) {
                    fill(255,127,36);
                } else if (cell.getCellState() == CellState.DESTROYED){
                    fill(255,0,0);
                }

                // Draw cell at the current position (row, col)
                rect(col * cellSize + 700, row * cellSize + 200, cellSize, cellSize);
            }
        }
        textSize(15);
        fill(0);
        text("Your Ships:", 700, 180);
    }

    private void drawInvPlayerShipsOnGrid(Grid invPlayerShipsOnCompGrid) {
        int cellSize = 40;

        for (int row = 0; row < invPlayerShipsOnCompGrid.getPosition().getPosY(); row++) {
            for (int col = 0; col < invPlayerShipsOnCompGrid.getPosition().getPosX(); col++) {
                // Cell at the current position (row, col)
                Cell cell = invPlayerShipsOnCompGrid.getField(invPlayerShipsOnCompGrid.getAbc()[row], col);


                // Check the status of the cell and draw accordingly
                if (cell.getCellState() == CellState.UNKNOWN) {
                    noFill();
                } else if (cell.getCellState() == CellState.WATER) {
                    noFill();
                } else if (cell.getCellState() == CellState.SHIP) {
                    fill(165,42,42);
                } else if (cell.getCellState() == CellState.HIT) {
                    noFill();
                }

                // Draw cell at the current position (row, col)
                rect(col * cellSize + 700, row * cellSize + 200, cellSize, cellSize);
            }
        }
    }

    /**
     * Handles keyboard input events from user.
     *
     * @param event The KeyEvent object representing the keyboard input event.
     *
     */
    @Override
    public void keyPressed(KeyEvent event){
        // vertical ship position
        if(event.getKeyCode() == UP) controller.handleKeyboardInput('v');
        if(event.getKeyCode() == DOWN) controller.handleKeyboardInput('v');

        // horizontal ship position
        if(event.getKeyCode() == LEFT) controller.handleKeyboardInput('h');
        if(event.getKeyCode() == RIGHT) controller.handleKeyboardInput('h');

        controller.handleKeyboardInput(event.getKey());
    }

    /**
     * Handles mouse click events from user.
     */
    @Override
    public void mousePressed() {
        int cellSize = 50;
        int col = 0;
        int row = 0;

        // Convert mouse position to grid coordinates
        if(controller.getGameState() == GameState.PLACE_SHIPS){
            col = (mouseX - (width/2 - 200)) / cellSize;
            row = (mouseY - (height/2 - 200)) / cellSize;
            fill(255,255,0);
            rect(col * cellSize + ((float) width/2 - 200), row * cellSize + ((float) height/2 - 200), cellSize, cellSize);
        }
        if(controller.getGameState() == GameState.PLAYING){
            col = (mouseX - 100) / cellSize;
            row = (mouseY - 200) / cellSize;
            fill(255,255,0);
            rect(col * cellSize + 100, row * cellSize + 200, cellSize, cellSize);
        }
        if (col < 0 || col > 10 || row < 0  || row > 10) {
            System.out.println("Pressed mouse outside the field");
            return;
        }
        controller.handleMouseInput(row, col);
    }


}
