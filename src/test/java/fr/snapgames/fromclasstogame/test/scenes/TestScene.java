package fr.snapgames.fromclasstogame.test.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.physic.World;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.core.system.SystemManager;


public class TestScene extends AbstractScene {

    public TestScene(Game g) {
        super(g, "test");
    }

    public TestScene(Game g, String name) {
        super(g, name);
    }

    @Override
    public void create(Game g) throws UnknownResource {
        super.create(g);
        World w = new World(800, 600);
        w.setGravity(new Vector2d(0.0, 0.0));
        g.getPhysicEngine().setWorld(w);
        g.getRenderer().setWorld(w);
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
