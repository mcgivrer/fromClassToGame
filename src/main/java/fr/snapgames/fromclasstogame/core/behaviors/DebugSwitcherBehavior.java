package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.scenes.Scene;

import java.awt.event.KeyEvent;

public class DebugSwitcherBehavior implements Behavior<Scene> {
    private static int cpt = 0;

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
    public void onUpdate(Scene go, long dt) {

    }

    @Override
    public void onRender(Scene go, Render r) {

    }

    @Override
    public void onAction(Scene go, Integer action) {

    }

    private void switchDebugLevel(Scene go) {
        int d = go.getGame().getWindow().getDebug();
        d = d < 5 ? d + 1 : 0;
        go.getGame().getWindow().setDebug(d);
    }

}
