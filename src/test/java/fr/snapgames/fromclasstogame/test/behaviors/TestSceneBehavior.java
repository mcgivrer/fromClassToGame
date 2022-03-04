package fr.snapgames.fromclasstogame.test.behaviors;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.scenes.Scene;

public class TestSceneBehavior implements Behavior<Scene> {
    @Override
    public void onInput(Scene go, ActionHandler ih) {
        System.out.println("- input from behavior TestBehavior");
    }

    @Override
    public void onUpdate(Scene go, long dt) {
        System.out.println("- update from behavior TestBehavior");

    }

    @Override
    public void onRender(Scene go, Renderer r) {
        System.out.println("- render form behavior TestBehavior");

    }

    @Override
    public void onAction(Scene go, Integer action) {
        // this behavior has nothing to do with action.
    }
}
