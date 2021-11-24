package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.entity.GameObject;

/**
 * The World Object is a GameObject that will never be rendered but used by others to.
 */
public class World extends GameObject {

    /**
     * Max velocity for ény object in this world.
     */
    public double maxVelocity = 8.0;
    /**
     * default gravity in this world.
     */
    public Vector2d gravity = new Vector2d(0, -0.981);

    /**
     * Let's initialized the world playground.
     *
     * @param width  of the world
     * @param height of the world
     */
    public World(double width, double height) {
        super("world");
        this.width = width;
        this.height = height;
    }

    /**
     * Set new gravity for this World.
     *
     * @param g the new Gravity 2D vector.
     * @return
     */
    public World setGravity(Vector2d g) {
        this.gravity = g;
        return this;
    }
}
