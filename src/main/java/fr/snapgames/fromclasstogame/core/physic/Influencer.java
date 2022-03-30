package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.entity.AbstractEntity;
import fr.snapgames.fromclasstogame.core.entity.Entity;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox.BoundingBoxType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An Influencer is an area in the world applying some influence to the intersecting GameObject.
 *
 * @author Frédéric Delorme
 * @since 0.0.3
 */
public class Influencer extends AbstractEntity<Influencer>{
    public BoundingBox area;
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
    private BoundingBoxType type = BoundingBoxType.RECTANGLE;


    /**
     * Create a new Influencer name, applying force to the area with a level of energy.
     *
     * @param name   Name for this Influencer
     * @param force  a Vector2D force to be applied to any intersecting GameObject
     * @param area   area of influence where to apply effect
     * @param energy the energy factor to be applied to the intersecting object.
     */
    public Influencer(String name, Vector2d force, BoundingBox area, double energy) {
        this.name = name;
        this.force = force;
        this.area = area;
        this.energy = energy;
        this.position = Utils.add(area.position, new Vector2d(area.shape.width / 2.0, area.shape.height / 2.0));
    }


    /**
     * Compute energy influence for the otherPosition object according to energy of this {@link Influencer}.
     *
     * @param otherPosition Position of the other object to define influence of this force.
     * @return value of the resulting influence on the object.
     */
    public double getInfluenceAtPosition(Vector2d otherPosition) {
        if (this.energy > 0) {
            double dx = this.position.x - otherPosition.x;
            double dy = this.position.y - otherPosition.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            double factor = 100.0 / distance;

            return factor;
        } else {
            return 1.0;
        }
    }

    /**
     * Return debug information for debug display mode.
     *
     * @return
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
                debugInfo.add("type:" + type);
            }
            debugInfo.add(String.format("force:%s", force));
            debugInfo.add(String.format("energy:%f", energy));
        }
        return debugInfo;
    }

    public Influencer setInfluenceAreaType(BoundingBoxType type) {
        this.type = type;
        return this;
    }

    public Influencer setDebugFillColor(Color dfc) {
        this.debugFillColor = dfc;
        return this;
    }

    public Influencer setDebugLineColor(Color dlc) {
        this.debugLineColor = dlc;
        return this;
    }
}