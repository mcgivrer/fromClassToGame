package fr.snapgames.fromclasstogame.test.behaviors;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.io.InputHandler;

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
    public void onRender(GameObject go, Render r) {
        System.out.println("- render form behavior TestBehavior");

    }

    @Override
    public void onAction(GameObject go, Integer action) {
        // this behavior has nothing to do with action.
    }
}
