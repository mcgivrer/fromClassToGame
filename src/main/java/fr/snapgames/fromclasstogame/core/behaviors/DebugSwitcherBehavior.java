package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;

/**
 * Behavior to add the specific Debug Mode to the Scene.
 */
public class DebugSwitcherBehavior implements Behavior<Scene> {

    // new action defined for all scenes.
    public static final int DEBUG_ACTIVE_FLAG = ActionHandler.ACTIONS_INTERNAL + 0;
    public static final int DEBUG_NEXT_ELEMENT = ActionHandler.ACTIONS_INTERNAL + 1;
    public static final int DEBUG_PREV_ELEMENT = ActionHandler.ACTIONS_INTERNAL + 2;
    public static final int DEBUG_LEVEL_PLUS = ActionHandler.ACTIONS_INTERNAL + 3;
    public static final int DEBUG_LEVEL_MINUS = ActionHandler.ACTIONS_INTERNAL + 4;

    private static final Logger logger = LoggerFactory.getLogger(DebugSwitcherBehavior.class);

    private static int cpt = 0;
    int objIdx = 0;
    int debugLevel = 2;

    public DebugSwitcherBehavior() {

        ActionHandler ah = (ActionHandler) SystemManager.get(ActionHandler.class);
        ah.registerAction(this.DEBUG_ACTIVE_FLAG, KeyEvent.VK_D);
        ah.registerAction(this.DEBUG_NEXT_ELEMENT, KeyEvent.VK_TAB);
        ah.registerAction(this.DEBUG_PREV_ELEMENT, KeyEvent.VK_BACK_SPACE);
        ah.registerAction(this.DEBUG_LEVEL_PLUS, KeyEvent.VK_N);
        ah.registerAction(this.DEBUG_LEVEL_MINUS, KeyEvent.VK_B);
    }


    @Override
    public void onAction(Scene scene, Integer action) {
        switch (action) {
            case DEBUG_ACTIVE_FLAG:
                switchDebugLevel(scene);

            case DEBUG_NEXT_ELEMENT:
                rotateDebugActiveElement(scene, 1);
                break;
            case DEBUG_PREV_ELEMENT:
                rotateDebugActiveElement(scene, -1);
                break;
            case DEBUG_LEVEL_PLUS:
                switchCurrentElementLevelDebug(scene, +1);
                break;
            case DEBUG_LEVEL_MINUS:
                switchCurrentElementLevelDebug(scene, -1);
                break;
            default:
                break;
        }
    }

    private void rotateDebugActiveElement(Scene scene, int direction) {
        objIdx += direction;
        if (objIdx < 0) {
            objIdx = scene.getObjectsList().size() - 1;
        }
        if (objIdx > scene.getObjectsList().size()) {
            objIdx = 0;
        }

        if (objIdx + direction < scene.getObjectsList().size() && objIdx > -1) {
            switchCurrentElementLevelDebug(scene, 0);
        }

        scene.getGame().getWindow().addDebugStatusElement("actDbgElt", "[" + objIdx + "]" + scene.getObjectsList().get(objIdx).name);
    }

    private void switchCurrentElementLevelDebug(Scene scene, int upDown) {

        int elementDebugLevel = scene.getObjectsList().get(objIdx).getDebug();
        elementDebugLevel += upDown;
        elementDebugLevel = elementDebugLevel < 0 ? 0 : elementDebugLevel > 5 ? 5 : elementDebugLevel;
        scene.getObjectsList().get(objIdx).setDebug(elementDebugLevel);
        scene.getGame().getWindow().addDebugStatusElement("actDbgLvl", "" + elementDebugLevel);

    }

    /**
     * Switch the debug level on the active game.
     *
     * @param scene the current active game.
     */
    private void switchDebugLevel(Scene scene) {
        int d = scene.getGame().getWindow().getDebug();
        d = d < 5 ? d + 1 : 0;
        scene.getGame().getWindow().setDebug(d);
    }

}
