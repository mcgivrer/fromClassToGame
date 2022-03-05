package fr.snapgames.fromclasstogame.test.behaviors;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;

public class TestBehavior implements Behavior<GameObject> {
    @Override
    public void onInput(GameObject entity, ActionHandler ah) {
        System.out.println("- input from behavior TestBehavior");
    }

    @Override
    public void onUpdate(GameObject entity, long elapsed) {
        System.out.println("- update from behavior TestBehavior");

    }

    @Override
    public void onRender(GameObject go, Renderer renderer) {
        System.out.println("- render form behavior TestBehavior");

    }

    @Override
    public void onAction(GameObject entity, Integer action) {
        // this behavior has nothing to do with action.
    }
}
