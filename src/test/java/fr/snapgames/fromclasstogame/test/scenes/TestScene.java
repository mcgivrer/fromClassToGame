package fr.snapgames.fromclasstogame.test.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.InputHandler;
import fr.snapgames.fromclasstogame.core.physic.World;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;


public class TestScene extends AbstractScene {


    private String name;

    public TestScene(Game g) {
        super(g);
        name = "test";

    }

    public TestScene(Game g, String name) {
        this(g);
        this.name = name;
    }


    @Override
    public String getName() {

        return name;
    }

    @Override
    public void create(Game g) throws UnknownResource {
        super.create(g);
        World w = new World(800,600);
        w.setGravity(0.0);
        g.setWorld(w);
    }

    @Override
    public void input(InputHandler inputHandler) {
        // Nothing to do there for test only purpose

    }

    @Override
    public void render() {
        // Nothing to do there for test only purpose

    }

    @Override
    public void dispose() {
        // Nothing to do there for test only purpose

    }

}
