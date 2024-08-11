package battleship.model;

import java.util.Arrays;
import java.util.Random;


/**
 * Represents the model for playing Battleship in single-player mode against the computer.
 * Manages game initialization, ship placement and shooting for game flow.
 *
 * Supports JShell output, example:
 *
 * jshell --class-path ./out/production/Battleship
 * jshell> import battleship.model.Model
 *
 * jshell> var game = new Model()
 * Welcome to Battleship!
 * Player Ships:
 *
 *    1 2 3 4 5 6 7 8 9 10
 *  A
 *  B
 *  C
 *  D
 *  E
 *  F
 *  G
 *  H
 *  I
 *  J
 *
 *
 *  Enemy Ships:
 *
 *    1 2 3 4 5 6 7 8 9 10
 *  A
 *  B
 *  C
 *  D
 *  E
 *  F
 *  G
 *  H
 *  I
 *  J
 *
 *  jshell> game.buildShip('B',2)
 *  Ship Placement: current ship length: 5
 *
 *
 *  Player Ships:
 *
 *    1 2 3 4 5 6 7 8 9 10
 *  A
 *  B   S S S S S
 *  C
 *  D
 *  E
 *  F
 *  G
 *  H
 *  I
 *  J
 *
 *  jshell> game.setVerticalDirection()
 *
 *  jshell> game.getDirection()
 *  $5 ==> 1
 *
 *  jshell> game.buildShip('D',8)
 *  Ship Placement: current ship length: 4
 *
 *  Player Ships:
 *
 *    1 2 3 4 5 6 7 8 9 10
 *  A
 *  B   S S S S S
 *  C
 *  D               S
 *  E               S
 *  F               S
 *  G               S
 *  H
 *  I
 *  J
 *
 *  jshell> game.getInvPlayerShipsOnCompGrid()
 *  $8 ==>   1 2 3 4 5 6 7 8 9 10
 *  A
 *  B   S S S S S
 *  C
 *  D               S
 *  E               S
 *  F               S
 *  G               S
 *  H
 *  I
 *  J
 *
 *
 *  jshell> game.playershoot('B',2)
 *  Your shot was MISSED.
 *
 *  Player Ships:
 *
 *    1 2 3 4 5 6 7 8 9 10
 *  A
 *  B   S S S S S
 *  C
 *  D               S
 *  E               S
 *  F               S
 *  G               S
 *  H
 *  I
 *  J
 *
 *
 *  Enemy Ships:
 *
 *    1 2 3 4 5 6 7 8 9 10
 *  A
 *  B   W
 *  C
 *  D
 *  E
 *  F
 *  G
 *  H
 *  I
 *  J
 *
 *  jshell> game.computerShoot()
 *  Computer's turn to shoot.
 *  Computer shot at: H 1
 *  Computer shot was MISSED.
 *
 *  Player Ships:
 *
 *    1 2 3 4 5 6 7 8 9 10
 *  A
 *  B   S S S S S
 *  C
 *  D               S
 *  E               S
 *  F               S
 *  G               S
 *  H W
 *  I
 *  J
 *
 *
 *  Enemy Ships:
 *
 *    1 2 3 4 5 6 7 8 9 10
 *  A
 *  B   W
 *  C
 *  D
 *  E
 *  F
 *  G
 *  H
 *  I
 *  J
 *
 */


public class Model {
    private Data data;
    private Random random = new Random();
    private boolean isPlayer;
    private int direction = 0; // default horizontal
    private int currentShipIndex = 0;

    /**
     * Initializes the Battleship game.
     */
    public Model(){initGame();}

    private void initGame(){
        data = new Data();
        System.out.println("Welcome to Battleship!");

        data.playerGrid = new Grid(10,10);
        data.playerShips = new Ship[data.shipLengths.length];

        data.computerGrid = new Grid(10,10);
        data.computerShips = new Ship[data.shipLengths.length];

        // Invisible grids to place the ships, necessary to hide ship positions
        data.invPlayerShipsOnGrid = new Grid(10,10);
        data.invCompGrid = new Grid(10,10);
        randomizeComputerShips();
        updateGrids();
    }

    /**
     * Starts the ship placement phase and prompts the player to choose the ship direction.
     */
    public void startShipPlacement(){System.out.println("First you have to place your Ships on the Grid. \nChoose the Ship direction: \nPress 'UP' or 'DOWN' for vertical \nPress 'LEFT' or 'RIGHT' for horizontal");}

    /**
     * Builds a ship on the player's grid based on the specified row and column.
     *
     * @param row The row (character) where the ship will be placed.
     * @param col The column (integer) where the ship will start.
     */
    public void buildShip(char row, int col) {
        int currentShipLength = (data.shipLengths[currentShipIndex]);
        System.out.println("Ship Placement: current ship length: " + data.shipLengths[currentShipIndex]);
        int intRow = ((int) row + 1) - 'A';

        // Check whether the ship can fit in the selected direction
        if ((direction == 0 && col + currentShipLength <= data.invPlayerShipsOnGrid.getPosition().getPosX()+1)||
                (direction == 1 && intRow + currentShipLength <= data.invPlayerShipsOnGrid.getPosition().getPosY()+1)) {

            data.playerShips[currentShipIndex] = new Ship(new Cell[currentShipLength]); // selected ship

            for (int i = 0; i <= currentShipLength; i++) {
                Cell currentCell = data.invPlayerShipsOnGrid.getField(data.invPlayerShipsOnGrid.getAbc()[row - 'A'], col);

                // Check whether the cell is already in use
                if (currentCell.getCellState() != CellState.UNKNOWN) {
                    System.out.println("Ship cannot be placed. Select other start coordinates.");
                    return;
                }
            }

            // Place the ship and update the playing field
            setShipOnGrid(true, intRow-1, col-1, getDirection(), currentShipLength, data.playerShips[currentShipIndex].getCells());
            markFieldWithS(data.playerShips[currentShipIndex], data.playerShips[currentShipIndex].getCells());

            updateGrids();
            currentShipIndex++;

            allShipsSet(currentShipIndex);

        } else {System.out.println("Ship cannot be placed. Select other start coordinates.");}
    }

    /**
     * Checks if all ships are set on the grid for the current player.
     *
     * @param currentShipIndex The index of the current ship being placed.
     * @return True if all ships are set, false otherwise.
     */
    public boolean allShipsSet(int currentShipIndex){
        if (currentShipIndex >= data.shipLengths.length){return true;} // GameState PLAYING
        else{return false;} // GameState PLACE_SHIPS
    }

    void setShipOnGrid(boolean isPlayer, int y, int x, int direction, int shipLength, Cell[] shipCells){
        // positions the ship on the selected fields
        for (int i = 0; i < shipLength; i++) {
            // direction 0: horizontal, direction 1: vertical
            Position position = (direction == 0) ? new Position(y, x + i) : new Position(y + i, x);

            shipCells[i] = isPlayer ? data.invPlayerShipsOnGrid.getField(data.invPlayerShipsOnGrid.getAbc()[position.getPosY()], position.getPosX())
                    : data.invCompGrid.getField(data.invCompGrid.getAbc()[position.getPosY()], position.getPosX());
        }
    }

    void markFieldWithS(Ship ship, Cell[] shipCells){
        // mark cells on grid with 'S'
        for (Cell cell : shipCells)
            cell.setCellState(CellState.SHIP);

        ship.setCells(shipCells);
    }

    private boolean shipCollisionControl(int y, int x, int direction, int shipLength) {
        for (int i = 0; i < shipLength; i++) {
            // 0: horizontal , 1: vertical
            Position position = (direction == 0) ? new Position(y , x +i) : new Position(y + i, x);

            if(!isValidPosition(position)) {return false;}

            Cell cell = isPlayer ? data.invPlayerShipsOnGrid.getField(data.invPlayerShipsOnGrid.getAbc()[position.getPosY()], position.getPosX())
                    : data.invCompGrid.getField(data.invCompGrid.getAbc()[position.getPosY()], position.getPosX());


            if (!isCellAvailable(cell)) {return false;}

            if(isPlayer) {
                System.out.println("Ungültige Platzierung. Bitte wähle andere Startkoordinaten:");
                return false;
            }
        }
        return true;
    }

    private boolean isCellAvailable(Cell cell) {
        return cell.getCellState().equals(CellState.UNKNOWN) &&
                (isPlayer ? isCellAvailableForShip(data.playerShips, cell.getPosition()) : isCellAvailableForShip(data.computerShips, cell.getPosition()));
    }

    private boolean isValidPosition(Position position) {
        if (!isPlayer && (position.getPosY() < 0 || position.getPosY() >= data.invCompGrid.getPosition().getPosY() ||
                position.getPosX() < 0 || position.getPosX() >= data.invCompGrid.getPosition().getPosX())) {return false;}

        return true;
    }


    private void placeCompShips(Ship[] ships, int[] shipLengths, Random rand) {
        for (int i = 0; i < ships.length; i++) {
            ships[i] = new Ship(new Cell[shipLengths[i]]);
            checkComShipPlacement(ships[i], rand);
        }
    }
    private void randomizeComputerShips() {placeCompShips(data.computerShips, data.shipLengths, random);}
    private void checkComShipPlacement(Ship ship, Random rand) {
        int shipLength = ship.getLength();
        Cell[] shipCells = new Cell[shipLength];

        int y = 0;
        int x = 0;
        boolean isValidPlacement = false;
        int direction = rand.nextInt(2);

        while (!isValidPlacement) {
            y = rand.nextInt(10);
            x = rand.nextInt(10);

            if (direction == 0) {while (x + shipLength > 10) {x = rand.nextInt(10);} }// horizontal
             else {while (y + shipLength > 10) {y = rand.nextInt(10);} }// vertical

            isValidPlacement = shipCollisionControl(y,x,direction,shipLength);
        }

        setShipOnGrid(false, y, x, direction, shipLength, shipCells);
        markFieldWithS(ship, shipCells);
    }

    private boolean isCellAvailableForShip(Ship[] ships, Position position) {
        for (Ship existingShip : ships)
            if (existingShip != null)
                for (Cell cell : existingShip.getCells())
                    if (cell != null && cell.getPosition().getPosY() == position.getPosY() && cell.getPosition().getPosX() == position.getPosX()) { return false; } // Zelle ist bereits von einem anderen Schiff besetzt

        return true;
    }

    void shootMissed(boolean isPlayer, Cell target, Cell copy){
        if (target.getCellState() == CellState.UNKNOWN) {
            target.setCellState(CellState.WATER);
            copy.setCellState(CellState.WATER);
            if (isPlayer) {System.out.println("Your shot was MISSED.");}
                else {System.out.println("Computer shot was MISSED.");}
        }
    }

    void shootHitAShip(boolean isPlayer, Cell target, Cell copy){
        target.setCellState(CellState.HIT);
        copy.setCellState(CellState.HIT);
        System.out.println("You HIT a Ship!");
        if (isPlayer) {System.out.println("Your shot was HIT an enemy ship!");}
        else {System.out.println("Computer HIT one of your ships!");}
    }

    void shootDestroyedAShip(boolean isPlayer, Cell target, Cell copy){
        target.setCellState(CellState.DESTROYED);
        copy.setCellState(CellState.DESTROYED);
        if (isPlayer) {System.out.println("Your shot DESTROYED an enemy ship!");}
        else {System.out.println("Computer DESTROYED one of your ships!");}
    }

    /**
     * Shoots at a specified position on the grid during the player's turn.
     *
     * @param row The row (character) where the player wants to shoot.
     * @param col The column (integer) where the player wants to shoot.
     */
    public boolean playershoot(char row, int col) {
        int intRow = ((int) row + 1) - 'A';

        if (col <= data.invCompGrid.getPosition().getPosX() && intRow <= data.invCompGrid.getPosition().getPosY()) {
            Cell clickedCell = data.invCompGrid.getField(row, col - 1);
            Cell copyCellToPlayerGrid = data.playerGrid.getField(row, col - 1);

            if (clickedCell.getCellState() == CellState.UNKNOWN) {
                shootMissed(true, clickedCell, copyCellToPlayerGrid);
            } else if (clickedCell.getCellState() == CellState.SHIP) {
                shootHitAShip(true, clickedCell, copyCellToPlayerGrid);

                // proof if ship is sunk
                Ship hitShip = getShipByCell(data.computerShips, clickedCell);
                if (hitShip != null && isShipSunk(hitShip)) {
                    shootDestroyedAShip(true, clickedCell, copyCellToPlayerGrid);

                    // only update the cell status when the ship has been sunk
                    Cell[] destroyedShip = hitShip.getCells();
                    for (int i = 0; i < destroyedShip.length; i++)
                        destroyedShip[i].setCellState(CellState.DESTROYED);

                }
            } else {
                System.out.println("You already know this Field. Please choose another Field.");
                return false;
            }

            updateGrids();
            // check whether all ships have been sunk to end the game
            checkWinCondition(true);
        }
        return true;
    }

    /**
     * Checks the win condition for the specified player.
     * Therefore, check if all ships of the specified player are destroyed.
     *
     * @param isPlayer True if checking for the player, false for the computer.
     */
    public void checkWinCondition(boolean isPlayer){
        if (areAllShipsSunk(isPlayer? data.computerShips : data.playerShips)) {
            if(isPlayer) {System.out.println("Game Over! YOU WIN! \n");}
            else {System.out.println("Game Over! COMPUTER WIN! \n");}
        }
    }

    /**
     * Initiates the computer's turn to shoot.
     */
    public void computerShoot() {
        System.out.println("Computer's turn to shoot.");

        // random computer shoot
        int col = random.nextInt(10);
        int row = random.nextInt(10);
        char charRow = data.invPlayerShipsOnGrid.getAbc()[row];

        Cell targetCell = data.invPlayerShipsOnGrid.getField(charRow, col);
        System.out.println("Computer shot at: " + charRow + " " + col);

        // computer has already shot at this field and must select a new target
        if (targetCell.getCellState() != CellState.UNKNOWN && targetCell.getCellState() != CellState.SHIP) { computerShoot();}
            else{
                Cell copyCellToPlayerGrid = data.computerGrid.getField(charRow, col);

                // execute shot
                if (targetCell.getCellState() == CellState.SHIP) {
                    shootHitAShip(false, targetCell, copyCellToPlayerGrid);

                    // proof if ship is sunk
                    Ship hitShip = getShipByCell(data.playerShips, targetCell);
                    if (hitShip != null && isShipSunk(hitShip))
                        shootDestroyedAShip(false, targetCell, copyCellToPlayerGrid);


                } shootMissed(false, targetCell, copyCellToPlayerGrid);

                updateGrids();
                checkWinCondition(false);
            }

    }

    Ship getShipByCell(Ship[] ships, Cell targetCell) {
        for (Ship ship : ships)
            if (ship != null && Arrays.asList(ship.getCells()).contains(targetCell)) {return ship;}

        return null;
    }
    private boolean isShipSunk(Ship ship) {
        if (ship == null) {return false;}

        for (Cell cell : ship.getCells())
            if (cell.getCellState() != CellState.HIT && cell.getCellState() != CellState.DESTROYED) {return false;}

        return true;
    }

    /**
     * Checks if all ships are sunk.
     *
     * @param ships An array of ships to check.
     * @return true if all ships are sunk, false otherwise.
     */
    public boolean areAllShipsSunk(Ship[] ships) {
        for (Ship ship : ships)
            if (ship != null && !isShipSunk(ship)) {return false;}

        return true;
    }


    // Ship placement state

    /**
     * Retrieves the ship placement direction.
     *
     * @return The ship placement direction (0 for horizontal, 1 for vertical).
     */
    public int getDirection(){return this.direction;}

    /**
     * Sets the ship placement direction to horizontal.
     */
    public void setHorizontalDirection(){this.direction = 0;}

    /**
     * Sets the ship placement direction to vertical.
     */
    public void setVerticalDirection(){this.direction = 1;}

    /**
     * Retrieves the current ship index in the ship placement state.
     *
     * @return The current ship index.
     */
    public int getCurrentShipIndex(){return this.currentShipIndex;}

    /**
     * Retrieves the lengths of the ships in the game.
     *
     * @return An array containing the lengths of the ships.
     */
    public int[] getShipLengths(){return data.shipLengths;}


    // Game flow

    /**
     * Retrieves the current player status.
     *
     * @return True if it's the player turn, false otherwise.
     */
    public boolean getPlayer(){return this.isPlayer;}

    /**
     * Retrieves the grid for the player with information about the player shoot.
     *
     * @return Grid with information about computer ships state (UNKNOWN, MISSED, HIT, DESTROY).
     */
    public Grid getPlayerGrid(){return data.playerGrid;}

    /**
     * Retrieves an array of ships representing the player's ships.
     *
     * @return An array of ships representing the player's ships.
     */
    public Ship[] getPlayerShips(){return data.playerShips;}

    /**
     * Retrieves an array of ships representing the computer's ships.
     *
     * @return An array of ships representing the computer's ships.
     */
    public Ship[] getCompShips(){return data.computerShips;}

    /**
     * Retrieves the grid for the computer with information about the computer shoot.
     *
     * @return Grid with information about player ships state (UNKNOWN, MISSED, HIT, DESTROY).
     */
    public Grid getComputerGrid(){return data.computerGrid;}

    /**
     * Retrieves the grid where the player ships where placed.
     *
     * @return Invisible grid (for the computer) with player ships
     */
    public Grid getInvPlayerShipsOnCompGrid(){return data.invPlayerShipsOnGrid;}

    /**
     * Retrieves the grid where the computer ships where placed.
     * (Special function: cheat mode for JShell.)
     *
     * @return Invisible grid (for the player) with computer ships
     */
    public Grid getInvCompShipsOnGrid(){return data.invCompGrid;}

    /**
     * Updates the display of player and computer grids.
     */
    public void updateGrids(){System.out.println("Player Ships:" + "\n\n" + data.invPlayerShipsOnGrid.toString() + "\n\n" + "Enemy Ships:" + "\n\n" + data.playerGrid.toString() + "\n\n");}

}
