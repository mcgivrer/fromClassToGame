package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.physic.System;

/**
 * The {@link CollisionSystem} will provide mechanism to detect collision between
 * {@link fr.snapgames.fromclasstogame.core.entity.GameObject} bounding boxes.
 * <p>
 * `BoundingBox` can have multiple shapes,and basically would be Rectangle, Circle or Ellipse, and Points.
 */
public class CollisionSystem extends System {

    public CollisionSystem(Game g) {
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
