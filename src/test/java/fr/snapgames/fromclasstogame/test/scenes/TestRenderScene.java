package fr.snapgames.fromclasstogame.test.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.test.entity.TestObject;

public class TestRenderScene extends AbstractScene {

    public TestRenderScene(Game g) {
        super(g, "testrender");
    }

    @Override
    public void initialize(Game g) {
        super.initialize(g);
    }

    @Override
    public void create(Game g) throws UnknownResource {
        TestObject to = new TestObject("test", 0, 0);
        add(to);
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
