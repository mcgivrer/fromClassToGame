package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Utils;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

/**
 * This {@link CopyObjectPosition} is a {@link Behavior} implementation is dedicated to automatic copy of the position of a target object to follow.
 * The `target` {@link GameObject} is followed by the object having this behavior and set the "behavior" applied object with the
 * addition of the target position and a specified `offset`.
 *
 * @author Frédéric Delorme
 * @since 1.0.1
 */
public class CopyObjectPosition implements Behavior<GameObject> {
    GameObject target;
    Vector2d offset;

    /**
     * Initialize the CopyObjectPosition with a target to be followed.
     *
     * @param target the GameObject to be followed.
     */
    public CopyObjectPosition(GameObject target) {
        this.target = target;
    }

    /**
     * Initialize the CopyObjectPosition with a target to be followed.
     *
     * @param target the GameObject to be followed.
     * @param offset the offset add to the target position to be followed.
     */
    public CopyObjectPosition(GameObject target, Vector2d offset) {
        this(target);
        this.offset = offset;
    }

    @Override
    public void onUpdate(GameObject entity, long elapsed) {
        GameObject cO = (GameObject) entity;
        Vector2d zero = new Vector2d(0, 0);
        cO.setPosition(Utils.add(target.position, this.offset));
        cO.setVelocity(zero);
        cO.setAcceleration(zero);
    }

}
