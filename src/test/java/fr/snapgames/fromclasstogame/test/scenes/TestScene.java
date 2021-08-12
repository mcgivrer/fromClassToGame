package fr.snapgames.fromclasstogame.test.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;

import java.lang.invoke.ConstantCallSite;

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
    public void input() {
    }

    @Override
    public void render() {
    }

    @Override
    public void dispose() {
    }

}
