import battleship.controller.Controller;
import battleship.model.Model;
import battleship.view.View;
import processing.core.PApplet;

/**
 * The main class responsible for initializing and starting the Battleship game.
 *
 * This class sets up instances of {@link Model}, {@link View}, and {@link Controller} and connects them
 * to ensure proper communication and functionality of the game. It then starts the Processing application to
 * run the game.
 *
 * @author Johanna Schwarz
 * @version 1.0
 */
public class Main {

    /**
     * The main method that initializes and starts the Battleship game.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        final int HEIGHT = 800;
        final int WIDTH = 1200;

        // Create instances of Model, View, and Controller
        var model = new Model();
        var view = new View(WIDTH, HEIGHT);
        var controller = new Controller();

        //Connect Model, View, Controller
        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);

        //Starts the processing application
        PApplet.runSketch(new String[]{"Battleship"}, view);
    }

}

