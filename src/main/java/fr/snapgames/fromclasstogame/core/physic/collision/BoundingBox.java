package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.entity.AbstractEntity;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.awt.geom.Ellipse2D;

public class BoundingBox {


    public BoundingBoxType type = BoundingBoxType.RECTANGLE;
    public Vector2d position;
    public Box shape;
    public Ellipse2D.Double ellipse;
    public double diam1;
    public double diam2;
    public Vector2d[] points;

    /**
     * Create a manual BoundingBox for special usage.
     *
     * @param position position in the playground for this {@link BoundingBox}
     * @param width    width of this {@link BoundingBox}
     * @param height   height of this {@link BoundingBox}
     * @param type     type of this {@link BoundingBox}
     */
    public BoundingBox(Vector2d position, double width, double height, BoundingBoxType type) {
        this();
        // position
        this.position = position;
        // box
        this.shape = new Box(position, width, height);
        this.ellipse = new Ellipse2D.Double(position.x, position.y, width, height);
        // type of BoundingBox
        this.type = type;
    }

    public BoundingBox() {
        this.position = new Vector2d();
        this.shape = new Box();
        this.ellipse = new Ellipse2D.Double();

    }


    public void update(AbstractEntity go) {
        position.x = go.position.x;
        position.y = go.position.y;
        switch (type) {
            case RECTANGLE:
                shape.update(go);
                break;
            case CIRCLE:
                updateEllipse(go);

            default:
                break;
        }
    }

    private void updateEllipse(AbstractEntity go) {
        ellipse.x = go.position.x + go.width / 2;
        ellipse.y = go.position.y + go.height / 2;
        ellipse.width = go.width;
        ellipse.height = go.height;
    }

    public void update(GameObject go, Vector2d offset) {
        position = go.position.add(offset);

        switch (type) {
            case RECTANGLE:
                shape.update(go, offset);
                break;
            case CIRCLE:

                break;
            default:
                break;
        }
    }

    public boolean intersect(BoundingBox b) {
        boolean result = false;
        switch (type) {
            case RECTANGLE:
                result = position.x - b.position.x < shape.width - b.shape.width;
                result &= position.y - b.position.y < shape.height - b.shape.height;
                break;
            case CIRCLE:
                double dx = position.x - b.position.x;
                double dy = position.y - b.position.y;
                result = Math.sqrt(dx * dx + dy * dy) < shape.width + b.shape.width;
                break;
            default:
                break;
        }
        return result;
    }

    public String toString() {
        return "(" + this.position.x + "," + this.position.y + "," + this.shape.width + "," + this.shape.height + ")";
    }

    public enum BoundingBoxType {
        POINT,
        RECTANGLE,
        CIRCLE,
        ELLIPSE
    }

}
