/**
 * Controller package for the Battleship game.
 * <p>
 * This package is designed to be independent of any processing functions.
 * It manages user input, modifies the model, and determines the content to be drawn by the view.
 * <p>
 * Additionally, this package contains the view and controller interfaces (IView and IController) as they are essential
 * for the functioning of the controller. This design allows for the possibility of having various view packages in
 * the future, all implementing IView and utilizing IController.
 *
 * @author Johanna Schwarz
 * @version 2.15
 */

package battleship.controller;