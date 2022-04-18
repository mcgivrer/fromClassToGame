package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

import java.util.List;

/**
 * An {@link Influencer} is an area in the {@link World} applying some influence
 * to the intersecting {@link GameObject}.
 * <p>
 * This {@link Influencer} can apply a {@link Influencer#force} to the contained {@link GameObject} of apply
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
     * Create a new Influencer name, applying force to the area with a level of energy.
     *
     * @param name Name for this Influencer
     */
    public Influencer(String name) {
        super(name);
        physicType = PEType.STATIC;
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
        double factor = 100 / ((this.box.shape.width / 2) - distance);

        return factor * energy;
    }

    /**
     * Return debug information for debug display mode.
     *
     * @return List of String containing debug information to be displayed in debug mode.
     */
    public List<String> getDebugInfo() {
        List<String> debugInfo = super.getDebugInfo();
        debugInfo.add(String.format("force:%s", force));
        debugInfo.add(String.format("energy:%f", energy));
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
     * Define the energy applied to the force applied to the objects in the influencer area.
     *
     * @param e a double factor value to be applied.
     * @return The updated Influencer.
     */
    public Influencer setEnergy(double e) {
        this.energy = e;
        return this;
    }

    @Override
    public Influencer setPosition(Vector2d position) {
        return (Influencer) super.setPosition(position);
    }
}
