package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.entity.GameObject;

import java.util.ArrayList;
import java.util.List;

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
    public Vector2d gravity = new Vector2d(0.0, 0.0);

    /**
     * Possible influence areas in this world (wind, magnetic, water flow, other ?)
     */
    public List<Influencer> influencers = new ArrayList<>();


    /**
     * Let's initialized the world playground.
     *
     * @param width  of the world
     * @param height of the world
     */
    public World(double width, double height) {
        super("world");
        this.physicType = PEType.STATIC;
        this.objectType = GOType.OTHER;
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

    /**
     * Add a child object to the world.
     *
     * @param go the child object to be added to the World.
     * @return this updated World
     */
    public World add(GameObject go) {
        addChild(go);
        return this;
    }
}
