package fr.snapgames.fromclasstogame.test.system;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.system.System;

/**
 * This is also a TestSystem  for test purpose, avoiding mockup.
 */
public class TestSystem2 extends System {
    public TestSystem2(Game g) {
        super(g);
    }

    @Override
    public String getName() {
        return TestSystem2.class.getName();
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
