package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.io.actions.ActionAlreadyExistsException;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.PhysicEngine;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;

public class DebugSwitcherBehavior implements Behavior<Scene> {

    private static final Logger logger = LoggerFactory.getLogger(DebugSwitcherBehavior.class);

    // new action defined for all scenes.
    public static final int DEBUG_ACTIVE_FLAG = ActionHandler.ACTIONS_INTERNAL + 0;
    public static final int DEBUG_NEXT_ELEMENT = ActionHandler.ACTIONS_INTERNAL + 1;
    public static final int DEBUG_PREV_ELEMENT = ActionHandler.ACTIONS_INTERNAL + 2;
    public static final int DEBUG_LEVEL_PLUS = ActionHandler.ACTIONS_INTERNAL + 3;
    public static final int DEBUG_LEVEL_MINUS = ActionHandler.ACTIONS_INTERNAL + 4;
    public static final int DEBUG_FLAG_PE_INFLUENCERS = ActionHandler.ACTIONS_INTERNAL + 5;
    public static final int DEBUG_FLAG_PE_GRAVITY = ActionHandler.ACTIONS_INTERNAL + 6;

    private static int cpt = 0;
    int objIdx = 0;
    int debugLevel = 2;

    /**
     * Initialize new short keys to activate/deactivate debug features.
     */
    public DebugSwitcherBehavior() {
        ActionHandler ah = (ActionHandler) SystemManager.get(ActionHandler.class);

        ah.registerAction(this.DEBUG_ACTIVE_FLAG, KeyEvent.VK_D);
        ah.registerAction(this.DEBUG_NEXT_ELEMENT, KeyEvent.VK_TAB);
        ah.registerAction(this.DEBUG_PREV_ELEMENT, KeyEvent.VK_BACK_SPACE);
        ah.registerAction(this.DEBUG_LEVEL_PLUS, KeyEvent.VK_N);
        ah.registerAction(this.DEBUG_LEVEL_MINUS, KeyEvent.VK_B);
        ah.registerAction(this.DEBUG_FLAG_PE_INFLUENCERS, KeyEvent.VK_I);
        ah.registerAction(this.DEBUG_FLAG_PE_GRAVITY, KeyEvent.VK_G);

    }


    @Override
    public void onAction(Scene entity, Integer action) {
        switch (action) {
            case DEBUG_ACTIVE_FLAG:
                switchDebugLevel(entity);
                break;
            case DEBUG_NEXT_ELEMENT:
                rotateDebugActiveElement(entity, 1);
                break;
            case DEBUG_PREV_ELEMENT:
                rotateDebugActiveElement(entity, -1);
                break;
            case DEBUG_LEVEL_PLUS:
                switchCurrentElementLevelDebug(entity, +1);
                break;
            case DEBUG_LEVEL_MINUS:
                switchCurrentElementLevelDebug(entity, -1);
                break;
            case DEBUG_FLAG_PE_INFLUENCERS:
                switchInfluencers();
                break;
            case DEBUG_FLAG_PE_GRAVITY:
                switchGravity();
                break;
            default:
                break;
        }
        entity.getGame().getWindow().addDebugStatusElement("actDbgElt", "[" + objIdx + "]" + entity.getObjectsList().get(objIdx).name);
        // add debug info about PhysicEngine.
        PhysicEngine pe = (PhysicEngine) SystemManager.get(PhysicEngine.class);
        pe.getDebugInfo().forEach((k, v) -> entity.getGame().getWindow().addDebugStatusElement(k, v.toString()));
    }

    private void switchInfluencers() {
        PhysicEngine pe = (PhysicEngine) SystemManager.get(PhysicEngine.class);
        boolean dif = pe.getDebugFlag(PhysicEngine.DEBUG_FLAG_INFLUENCERS);
        pe.setDebugFlag(PhysicEngine.DEBUG_FLAG_INFLUENCERS, !dif);
    }

    private void switchGravity() {
        PhysicEngine pe = (PhysicEngine) SystemManager.get(PhysicEngine.class);
        boolean dif = pe.getDebugFlag(PhysicEngine.DEBUG_FLAG_GRAVITY);
        pe.setDebugFlag(PhysicEngine.DEBUG_FLAG_GRAVITY, !dif);
    }

    private void rotateDebugActiveElement(Scene scene, int direction) {
        objIdx += direction;
        if (objIdx < 0) {
            objIdx = scene.getObjectsList().size() - 1;
        }

        if (objIdx > scene.getObjectsList().size() - 1) {
            objIdx = 0;
        }

        if (objIdx + direction < scene.getObjectsList().size() && objIdx > -1) {
            switchCurrentElementLevelDebug(scene, 0);
        }
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
