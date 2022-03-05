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
 * {@link GameObject} bounding boxes.
 * <p>
 * `BoundingBox` can have multiple shapes,and basically would be Rectangle, Circle or Ellipse, and Points.
 *
 * @author Frédéric Delorme
 * @since 1.0.2
 */
public class CollisionSystem extends System {

    /**
     * A list of response ( {@link ProcessBehavior]) to be applied
     */
    private Map<String, ProcessBehavior<GameObject>> responses = new HashMap<>();

    @Override
    public String getName() {
        return "CollisionSystem";
    }

    /**
     * Create the CollisionSystem
     *
     * @param g the parent game.
     */
    public CollisionSystem(Game g) {
        super(g);
    }

    /**
     * add a Response for an entity having a name containing the filter,
     *
     * @param filter the filtering string to detect entity to apply the {@link ProcessBehavior} on.
     * @param pb     the ProcessBehavior to be applied.>
     */
    public void addResponse(String filter, ProcessBehavior pb) {
        responses.put(filter, pb);
    }

    /**
     * Initialize the CollisionSystem with {@link Configuration}.
     *
     * @param config the Configuration object
     * @return 0 if OK.
     */
    @Override
    public int initialize(Configuration config) {
        return 0;
    }

    /**
     * Process all GameObject to apply corresponding response ProcessBehaviors.
     *
     * @param dt the elapsed time since previous call.
     */
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

    /**
     * Detect is object1 and object2 are colliding.
     *
     * @param object1 the GameObject to be tested with object2
     * @param object2 the GameObject to be tested on object1.
     * @return true if colliding.
     */
    public boolean collide(GameObject object1, GameObject object2) {
        return object1.bbox.intersect(object2.bbox);
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

    /**
     * Release all the response {@link ProcessBehavior}.
     */
    @Override
    public void dispose() {
        responses.clear();
    }
}
