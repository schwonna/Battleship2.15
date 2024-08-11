package battleship.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    @Test
    void shouldProofIfAllShipsAreSet() {
        Model model = new Model();

        // when all ships are set
        assertTrue(model.allShipsSet(model.getShipLengths().length));
        // when not all ships are set
        assertFalse(model.allShipsSet(model.getShipLengths().length - 1));
    }

    @Test
    void playerShouldMarkFieldWithS() {
        Model model = new Model();

        Ship ship = new Ship(new Cell[3]);
        Cell[] shipCells = new Cell[3];

        model.setShipOnGrid(true, 2,2, 1, ship.getLength(), shipCells);
        model.markFieldWithS(ship, shipCells);

        // Check if all cells in the ship have been marked with 'S'
        for (Cell cell : shipCells) {
            assertEquals(CellState.SHIP, cell.getCellState());
        }

        // Check if the ship has been set with the marked cells
        assertArrayEquals(shipCells, ship.getCells());
    }

    @Test
    void computerShouldMarkFieldWithS() {
        Model model = new Model();

        Ship ship = new Ship(new Cell[3]);
        Cell[] shipCells = new Cell[3];

        model.setShipOnGrid(false, 2,2, 1, ship.getLength(), shipCells);
        model.markFieldWithS(ship, shipCells);

        // Check if all cells in the ship have been marked with 'S'
        for (Cell cell : shipCells) {
            assertEquals(CellState.SHIP, cell.getCellState());
        }

        // Check if the ship has been set with the marked cells
        assertArrayEquals(shipCells, ship.getCells());
    }

    @Test
    void playerShootShouldMissAShip() {
        Model model = new Model();

        Cell target = new Cell(2,2);
        Cell copy = new Cell(2,2);
        target.setCellState(CellState.UNKNOWN);
        copy.setCellState(CellState.UNKNOWN);

        model.shootMissed(true, target, copy);

        // Check if the state of the cells has been updated to WATER
        assertEquals(CellState.WATER, target.getCellState());
        assertEquals(CellState.WATER, copy.getCellState());
    }

    @Test
    void computerShootShouldMissAShip() {
        Model model = new Model();

        Cell target = new Cell(2,2);
        Cell copy = new Cell(2,2);
        target.setCellState(CellState.UNKNOWN);
        copy.setCellState(CellState.UNKNOWN);

        model.shootMissed(false, target, copy);

        // Check if the state of the cells has been updated to WATER
        assertEquals(CellState.WATER, target.getCellState());
        assertEquals(CellState.WATER, copy.getCellState());
    }

    @Test
    void playerShootShouldHitAShip(){
        Model model = new Model();

        Cell target = new Cell(2,2);
        Cell copy = new Cell(2,2);
        target.setCellState(CellState.SHIP);
        copy.setCellState(CellState.SHIP);

        model.shootHitAShip(true, target, copy);

        // Check if the state of the cells has been updated to WATER
        assertEquals(CellState.HIT, target.getCellState());
        assertEquals(CellState.HIT, copy.getCellState());
    }

    @Test
    void computerShootShouldHitAShip(){
        Model model = new Model();

        Cell target = new Cell(2,2);
        Cell copy = new Cell(2,2);
        target.setCellState(CellState.SHIP);
        copy.setCellState(CellState.SHIP);

        model.shootHitAShip(false, target, copy);

        // Check if the state of the cells has been updated to WATER
        assertEquals(CellState.HIT, target.getCellState());
        assertEquals(CellState.HIT, copy.getCellState());
    }

    @Test
    void playerShouldDestroyAShip(){
        Model model = new Model();

        Cell target = new Cell(2,2);
        Cell copy = new Cell(2,2);
        target.setCellState(CellState.HIT);
        copy.setCellState(CellState.HIT);

        model.shootDestroyedAShip(true, target, copy);

        assertEquals(CellState.DESTROYED, target.getCellState());
        assertEquals(CellState.DESTROYED, copy.getCellState());
    }

    @Test
    void computerShouldDestroyAShip(){
        Model model = new Model();

        Cell target = new Cell(2,2);
        Cell copy = new Cell(2,2);
        target.setCellState(CellState.HIT);
        copy.setCellState(CellState.HIT);

        model.shootDestroyedAShip(false, target, copy);

        assertEquals(CellState.DESTROYED, target.getCellState());
        assertEquals(CellState.DESTROYED, copy.getCellState());
    }

    @Test
    void shouldSetVerticalDirection(){
        Model model = new Model();
        model.buildShip('B', 2);
        model.getDirection();
        model.setVerticalDirection();
        model.buildShip('D', 3);
        assertTrue(model.getDirection() == 1);
    }

    @Test
    void shouldSetHorizontalDirection(){
        Model model = new Model();
        model.buildShip('B', 2);

        model.getDirection();
        assertTrue(model.getDirection() == 0);

        model.setVerticalDirection();
        assertTrue(model.getDirection() == 1);

        model.buildShip('D', 3);
        model.setHorizontalDirection();
        model.buildShip('I', 1);
        assertTrue(model.getDirection() == 0);
    }

    @Test
    void playerShootOnlyOnceOnTheSameField() {
        Model model = new Model();

        boolean result = model.playershoot('A', 1);
        assertTrue(result);

        boolean invalidResult = model.playershoot('A', 1);
        assertFalse(invalidResult);

    }

    @Test
    void playershootInPlayergrid(){
        Model model = new Model();

        boolean resultValid = model.playershoot('A', 1);
        assertTrue(resultValid);

        resultValid = model.playershoot('A', 10);
        assertTrue(resultValid);

        resultValid = model.playershoot('J', 1);
        assertTrue(resultValid);

        resultValid = model.playershoot('J', 10);
        assertTrue(resultValid);
    }

    @Test
    void shouldGetAShipByCell(){
        Model model = new Model();

        model.buildShip('B', 2);
        Ship[] playerShips = model.getPlayerShips();
        Cell clickedCell = model.getInvPlayerShipsOnCompGrid().getField('B', 2);

        Ship foundShip = model.getShipByCell(playerShips, clickedCell);
        assertNotNull(foundShip);

        Cell[] foundShipCells = foundShip.getCells();


        // Überprüfen Sie, ob das gefundene Schiff die erwarteten Zellen enthält
        //Cell[] foundShipCells = foundShip.getCells();
        assertEquals(5, foundShipCells.length); // Passen Sie dies an, wenn ein Schiff aus mehreren Zellen besteht
        assertTrue(clickedCell.getCellState() == foundShipCells[0].getCellState());
        assertEquals(clickedCell.getPosition().getPosY(), foundShipCells[0].getPosition().getPosY());
    }

    @Test
    void checkRangeComputerShoot(){
        Model model = new Model();

        Random random = new Random();
        int col = random.nextInt(10);
        int row = random.nextInt(10);

        assertTrue(col > 0 && col <= 10);
        assertTrue(row > 0 && row <= 10);
    }

    @Test
    void checkComputerShoot(){
        Model model = new Model();

        Random random = new Random();
        int col = random.nextInt(10);
        int row = random.nextInt(10);
        char charRow = model.getInvPlayerShipsOnCompGrid().getAbc()[row];

        Cell targetCell = model.getInvPlayerShipsOnCompGrid().getField(charRow, col);

        // Assertions based on the expected behavior of computerShoot
        if (targetCell.getCellState() == CellState.SHIP) {
            assertTrue(targetCell.getCellState() == CellState.HIT || targetCell.getCellState() == CellState.DESTROYED);
            assertFalse(targetCell.getCellState() == CellState.UNKNOWN || targetCell.getCellState() == CellState.WATER);
            // Add more assertions based on your expected behavior for a hit
        } else {
            assertTrue(CellState.UNKNOWN.equals(targetCell.getCellState()) ||
                    CellState.WATER.equals(targetCell.getCellState()));
        }
    }

    @Test
    void shouldReturnPlayer(){
        Model model = new Model();
        model.playershoot('B',1);
        assertFalse(model.getPlayer());
        model.computerShoot();
    }

    @Test
    void shouldReturnCurrentShipIndex(){
        Model model = new Model();
        model.buildShip('B', 2);
        assertEquals(model.getCurrentShipIndex(), 1);
    }

    @Test
    void gridsShouldNotBeNull() {
        Model model = new Model();
        assertNotNull(model.getPlayerGrid());
        assertNotNull(model.getComputerGrid());
        assertNotNull(model.getInvCompShipsOnGrid());
    }

    @Test
    void shipsShouldNotBeNull(){
        Model model = new Model();
        assertNotNull(model.getPlayerShips());
        assertNotNull(model.getCompShips());

    }


}