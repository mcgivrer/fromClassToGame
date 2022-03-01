package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.behaviors.ProcessBehavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;

import java.util.Arrays;

/**
 * A collision event between 2 {@link GameObject} entities, produced by the {@link CollisionSystem}.
 *
 * @author Frédéric Delorme
 * @see CollisionSystem
 * @since 1.0.2
 */
public class Collision {
    /**
     * First {@link GameObject} in the collision
     */
    private final GameObject object1;
    /**
     * Second {@link GameObject} in the collision
     */
    private final GameObject object2;

    /**
     * Collision creation between o1 and o2.
     *
     * @param o1 the first {@link GameObject} in the collision
     * @param o2 the object colliding by the o1 {@link GameObject}.
     */
    public Collision(GameObject o1, GameObject o2) {
        this.object1 = o1;
        this.object2 = o2;
    }

    /**
     * Return the {@link GameObject} object1
     *
     * @return
     */
    public GameObject getObject1() {
        return object1;
    }

    /**
     * Return the {@link GameObject} object2
     *
     * @return
     */
    public GameObject getObject2() {
        return object2;
    }

    /**
     * Processing the collision by delegating to a {@link ProcessBehavior}.
     *
     * @param oec
     */
    public void process(ProcessBehavior<GameObject> oec) {
        oec.process(Arrays.asList(object1, object2));
    }
}
