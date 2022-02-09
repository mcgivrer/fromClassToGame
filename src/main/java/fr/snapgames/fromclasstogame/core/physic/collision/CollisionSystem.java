package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.OnEntityCollision;
import fr.snapgames.fromclasstogame.core.behaviors.ProcessBehavior;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.system.System;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link CollisionSystem} will provide mechanism to detect collision between
 * {@link fr.snapgames.fromclasstogame.core.entity.GameObject} bounding boxes.
 * <p>
 * `BoundingBox` can have multiple shapes,and basically would be Rectangle, Circle or Ellipse, and Points.
 */
public class CollisionSystem extends System {

    private Map<String, ProcessBehavior<GameObject>> responses = new HashMap<>();

    @Override
    public String getName() {
        return "CollisionSystem";
    }

    public CollisionSystem(Game g) {
        super(g);
    }

    public void addResponse(String filter, ProcessBehavior pb) {
        responses.put(filter, pb);
    }

    @Override
    public int initialize(Configuration config) {
        return 0;
    }

    public void update(long dt) {
        objects.forEach(o -> {
            for (GameObject object : objects) {
                o.setCollide(false);
                object.setCollide(false);
                if (collide(o, object)) {
                    collisionResponse(new Collision(o, object));
                }
            }
        });
    }

    public boolean collide(GameObject o1, GameObject o2) {
        return o1.bbox.intersect(o2.bbox);
    }

    /**
     * Process the collision response for the 2 identified GameObjects. Filtering object1 on its name according to filter property in the responses map.
     * If the filter match to object2 name, the corresponding ProcessBehavior to proceed.
     *
     * @param collision the produced collision ro be processed by the right ProcessBehavior.
     */
    public void collisionResponse(Collision collision) {
        for (Map.Entry<String, ProcessBehavior<GameObject>> response : responses.entrySet()) {
            if (collision.getObject1().name.contains(response.getKey())) {
                collision.process(response.getValue());
            }
        }
    }

    @Override
    public void dispose() {
        responses.clear();
    }
}
