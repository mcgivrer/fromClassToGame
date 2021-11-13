package fr.snapgames.fromclasstogame.test.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.io.InputHandler;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;

public class TestCameraScene extends AbstractScene {

    public TestCameraScene(Game g) {
        super(g, "camera-scene");
    }


    @Override
    public void create(Game g) throws UnknownResource {
        GameObject target = new GameObject("target",
                g.getRender().getBuffer().getWidth() / 2,
                g.getRender().getBuffer().getHeight() / 2);
        add(target);
        Camera cam01 = new Camera("cam01")
                .setViewport(g.getRender().getViewport())
                .setTarget(target)
                .setTweenFactor(1);
        add(cam01);
        Camera cam02 = new Camera("cam02")
                .setViewport(g.getRender().getViewport())
                .setTarget(target)
                .setTweenFactor(0.1);
        add(cam02);
    }

    @Override
    public void input(ActionHandler actionHandler) {
        // Nothing to do there for test only purpose
    }

    @Override
    public void dispose() {
        // Nothing to do there for test only purpose
    }
}
