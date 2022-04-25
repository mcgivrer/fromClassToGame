package fr.snapgames.fromclasstogame.test.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.test.entity.TestObject;

public class TestRenderObjectsScene extends AbstractScene {

    public TestRenderObjectsScene(Game g) {
        super(g, "testrender");
    }

    @Override
    public void initialize(Game g) {
        super.initialize(g);
    }

    @Override
    public void create(Game g) throws UnknownResource {
        for (int i = 0; i < 10; i++) {
            TestObject to = new TestObject("test_" + i, 0, 0);
            add(to);
        }
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
