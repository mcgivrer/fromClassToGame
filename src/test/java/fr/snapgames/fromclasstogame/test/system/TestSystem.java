package fr.snapgames.fromclasstogame.test.system;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.system.System;

public class TestSystem extends System {
    @Override
    public String getName() {
        return TestSystem.class.getName();
    }

    public TestSystem(Game g) {
        super(g);
    }

    @Override
    public int initialize(Configuration config) {
        return 0;
    }

    @Override
    public void dispose() {

    }
}
