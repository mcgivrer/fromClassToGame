package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

import java.util.List;

/**
 * An Influencer is an area in the world applying some influence to the intersecting GameObject.
 */
public class Influencer extends GameObject {
    /**
     * Default energy factor for the force.
     */
    public double energy = 1.0;
    /**
     * Influence Force for this influence Area
     */
    public Vector2d force = new Vector2d();
    /**
     * Bounding Box type for this Influencer.
     */
    private BoundingBox.BoundingBoxType type = BoundingBox.BoundingBoxType.RECTANGLE;

    /**
     * Create a new Influencer name, applying force to the area with a level of energy.
     *
     * @param name   Name for this Influencer
     * @param force  a Vector2D force to be applied to any intersecting GameObject
     * @param area   area of influence where to apply effect
     * @param energy the energy factor to be applied to the intersecting object.
     */
    public Influencer(String name, Vector2d force, BoundingBox area, double energy) {
        super(name);
        this.force = force;
        this.bbox = area;
        this.energy = energy;
        this.position = Utils.add(this.bbox.position, new Vector2d(this.bbox.diam1 / 2.0, this.bbox.diam2 / 2.0));
    }


    /**
     * Compute energy influence for the otherPosition object according to energy of this {@link Influencer}.
     *
     * @param otherPosition Position of the other object to define influence of this force.
     * @return value of the resulting influence on the object.
     */
    public double getInfluenceAtPosition(Vector2d otherPosition) {
        double dx = this.position.x - otherPosition.x;
        double dy = this.position.y - otherPosition.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double factor = 100 / ((this.bbox.shape.width / 2) - distance);

        return factor * energy;//
    }

    /**
     * Return debug information for debug display mode.
     *
     * @return
     */
    public List<String> getDebugInfo() {
        List<String> debugInfo = super.getDebugInfo();
        debugInfo.add(String.format("force:%s", force));
        debugInfo.add(String.format("energy:%f", energy));
        return debugInfo;
    }

    public Influencer setInfluenceAreaType(BoundingBox.BoundingBoxType type) {
        this.type = type;
        return this;
    }
}
