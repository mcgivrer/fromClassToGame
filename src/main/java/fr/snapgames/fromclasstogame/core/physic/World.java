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
    public Vector2d gravity = new Vector2d(0, -0.981);

    /**
     * Possible influence areas in this world (wind, magnetic, water flow, other ?)
     */
    public List<InfluenceArea2d> influenceAreas = new ArrayList<>();


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

    /**
     * Add an Influence area to the world.
     *
     * @param area the InfluenceArea to add to the World.
     * @return this updated World
     */
    public World addInfluenceArea(InfluenceArea2d area) {
        influenceAreas.add(area);
        return this;
    }
}
