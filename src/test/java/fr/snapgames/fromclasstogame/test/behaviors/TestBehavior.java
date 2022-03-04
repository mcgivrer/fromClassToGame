package fr.snapgames.fromclasstogame.test.behaviors;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;

public class TestBehavior implements Behavior<GameObject> {
    @Override
    public void onInput(GameObject go, ActionHandler ih) {
        System.out.println("- input from behavior TestBehavior");
    }

    @Override
    public void onUpdate(GameObject go, long dt) {
        System.out.println("- update from behavior TestBehavior");

    }

    @Override
    public void onRender(GameObject go, Renderer r) {
        System.out.println("- render form behavior TestBehavior");

    }

    @Override
    public void onAction(GameObject go, Integer action) {
        // this behavior has nothing to do with action.
    }
}
