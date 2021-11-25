package fr.snapgames.fromclasstogame.test.system;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.system.System;

/**
 * This is only a TestSystem Purpose, avoiding Mockup.
 */
public class TestSystem extends System {
    public TestSystem(Game g) {
        super(g);
    }

    @Override
    public String getName() {
        return TestSystem.class.getName();
    }

    @Override
    public int initialize(Configuration config) {
        return 0;
    }

    @Override
    public void dispose() {
        // nothing specific to be implemented here, only for test purpose.
    }
}
