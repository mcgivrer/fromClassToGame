package fr.snapgames.fromclasstogame.test.behaviors;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.InputHandler;

public class TestBehavior implements Behavior {
    @Override
    public void input(GameObject go, InputHandler ih) {
        System.out.println("- input from behavior TestBehavior");
    }

    @Override
    public void update(GameObject go, long dt) {
        System.out.println("- update from behavior TestBehavior");

    }

    @Override
    public void render(GameObject go, Render r) {
        System.out.println("- render form behavior TestBehavior");

    }
}
