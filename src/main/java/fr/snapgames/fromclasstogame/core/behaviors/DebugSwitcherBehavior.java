package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.core.scenes.Scene;

import java.awt.event.KeyEvent;

public class DebugSwitcherBehavior implements Behavior<Scene> {
    private static int cpt = 0;
    int objIdx = 0;
    int debugLevel = 2;

    @Override
    public void onInput(Scene go, ActionHandler ah) {
        if (ah.get(KeyEvent.VK_D)) {
            cpt++;
            if (cpt > 20) {
                cpt = 0;
                switchDebugLevel(go);
            }
        }

    }

    @Override
    public void onAction(Scene scene, Integer action) {
        switch (action) {
            case AbstractScene.DEBUG_NEXT_ELEMENT:
                rotateDebugActiveElement(scene);
                break;
            case AbstractScene.DEBUG_LEVEL_PLUS:
                switchCurrentElementLevelDebug(scene, +1);
                break;
            case AbstractScene.DEBUG_LEVEL_MINUS:
                switchCurrentElementLevelDebug(scene, -1);
                break;
            default:
                break;
        }
    }

    private void rotateDebugActiveElement(Scene scene) {
        if (objIdx + 1 < scene.getObjectsList().size()) {
            switchCurrentElementLevelDebug(scene, 0);
            objIdx++;
            switchCurrentElementLevelDebug(scene, 0);
        } else {
            switchCurrentElementLevelDebug(scene, 0);
            objIdx = 0;
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
