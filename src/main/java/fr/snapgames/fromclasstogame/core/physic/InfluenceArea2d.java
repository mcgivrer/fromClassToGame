package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class InfluenceArea2d {

    /**
     * Influence Force for this influence Area
     */
    public Vector2d force = new Vector2d();
    public BoundingBox influenceArea;
    public double energy = 1.0;

    public Vector2d position = new Vector2d();
    private BoundingBox.BoundingBoxType type = BoundingBox.BoundingBoxType.RECTANGLE;

    public InfluenceArea2d(Vector2d force, BoundingBox influenceArea, double energy) {
        this.force = force;
        this.influenceArea = influenceArea;
        this.energy = energy;
        this.position = Utils.add(this.influenceArea.position, new Vector2d(this.influenceArea.diam1 / 2.0, this.influenceArea.diam2 / 2.0));
    }

    public InfluenceArea2d setInfluenceAreaType(BoundingBox.BoundingBoxType type) {
        this.type = type;
        return this;
    }

    /**
     * Compute energy influence for the otherPosition object according to energy of this {@link InfluenceArea2d}.
     *
     * @param otherPosition Position of the other object to define influence of this force.
     * @return value of the resulting influence on the object.
     */
    public double getInfluenceAtPosition(Vector2d otherPosition) {
        double dx = this.position.x - otherPosition.x;
        double dy = this.position.y - otherPosition.y;
        return Math.sqrt(dx * dx + dy * dy) * energy;
    }

    public List<String> getDebugInfo() {
        List<String> debugInfo = new ArrayList<>();
        debugInfo.add(String.format("pos:%s", position));
        debugInfo.add(String.format("force:%s", force));
        debugInfo.add(String.format("energy:%f", energy));
        debugInfo.add(String.format("area:%s", influenceArea));

        return debugInfo;
    }
}
