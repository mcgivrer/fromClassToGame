package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.system.System;

/**
 * The {@link CollisionSystem} will provide mechanism to detect collision between
 * {@link fr.snapgames.fromclasstogame.core.entity.GameObject} bounding boxes.
 * <p>
 * `BoundingBox` can have multiple shapes,and basically would be Rectangle, Circle or Ellipse, and Points.
 */
public class CollisionSystem extends System {


    @Override
    public String getName() {
        return "CollisionSystem";
    }

    public CollisionSystem(Game g) {
        super(g);
    }

    @Override
    public int initialize(Configuration config) {
        return 0;
    }

    public void update(long dt) {
        objects.forEach(o -> {
            for (GameObject object : objects) {
                if (collide(o, object)) {
                    collisionResponse(o, object);
                }
            }
        });
    }

    public boolean collide(GameObject o1, GameObject o2) {

        return o1.bbox.intersect(o2.bbox);
    }

    public void collisionResponse(GameObject o1, GameObject o2) {

    }


    @Override
    public void dispose() {

    }
}
