package fr.snapgames.fromclasstogame.core.physic;

import java.util.ArrayList;
import java.util.List;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

/**
 * An {@link Influencer} is an area in the {@link World} applying some influence
 * to the intersecting {@link GameObject}.
 * <p>
 * This {@link Influencer} can apply a {@link Influencer#force} to the contained
 * {@link GameObject} of apply
 * the effect of a {@link Influencer#material}.
 * 
 * @author Frédéric Delorme
 * @see PhysicEngine
 * @since 0.0.3
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
     * Create a new Influencer name, applying force to the area with a level of
     * energy.
     *
     * @param name Name for this Influencer
     */
    public Influencer(String name) {
        super(name);
        physicType = PEType.STATIC;
    }

    /**
     * Compute energy influence for the otherPosition object according to energy of
     * this {@link Influencer}.
     *
     * @param otherPosition Position of the other object to define influence of this
     *                      force.
     * @return value of the resulting influence on the object.
     */
    public double getInfluenceAtPosition(Vector2d otherPosition) {
        if (this.energy > 0) {
            double dx = this.position.x - otherPosition.x;
            double dy = this.position.y - otherPosition.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            double factor = 100 / ((this.box.shape.width / 2) - distance);

            return factor;
        } else {
            return 1.0;
        }
    }

    /**
     * Return debug information for debug display mode.
     *
     * @return List of String containing debug information to be displayed in debug
     *         mode.
     */
    public List<String> getDebugInfo() {
        int debug = this.debugLevel;
        this.debugOffsetX = -40;
        this.debugOffsetY = 10;
        List<String> debugInfo = new ArrayList<>();
        if (debug > 0) {
            debugInfo.add("n:" + name);
            debugInfo.add("dbgLvl:" + debug);
            if (debug > 2) {
                debugInfo.add("pos:" + position.toString());
                debugInfo.add("type:" + this.box.type);
            }
            debugInfo.add(String.format("force:%s", force));
            debugInfo.add(String.format("energy:%f", energy));
        }
        return debugInfo;
    }

    public Influencer setInfluencerAreaType(BoundingBox.BoundingBoxType type) {
        this.box.type = type;
        return this;
    }

    /**
     * Define the Material applied to all object in this area.
     *
     * @param m The {@link Material} to be applied.
     * @return this {@link Influencer} updated.
     */
    public Influencer setMaterial(Material m) {
        return (Influencer) super.setMaterial(m);
    }

    /**
     * Apply the force f to all objets in the Influencer area.
     *
     * @param f the force to be applied.
     * @return the {@link Influencer} updated.
     */
    public Influencer setForce(Vector2d f) {
        this.force = f;
        return this;
    }

    /**
     * Define the energy applied to the force applied to the objects in the
     * influencer area.
     *
     * @param e a double factor value to be applied.
     * @return The updated Influencer.
     */
    public Influencer setEnergy(double e) {
        this.energy = e;
        return this;
    }

    /**
     * Override setPosition to transtype the returned object (for convience purpose)
     * 
     * @param position the new position of this object.
     * @return the updated Influencer
     */
    @Override
    public Influencer setPosition(Vector2d position) {
        return (Influencer) super.setPosition(position);
    }
}
