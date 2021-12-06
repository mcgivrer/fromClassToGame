package fr.snapgames.fromclasstogame.core.io.actions;

/**
 * This exception will be raised at runtime if the developer try to add an already existing
 * action code with the {@link ActionHandler#registerAction(int, int)}.
 *
 * @see ActionHandler
 */
public class ActionAlreadyExistsException extends Exception {
    public ActionAlreadyExistsException(String message) {
        super(message);
    }
}
