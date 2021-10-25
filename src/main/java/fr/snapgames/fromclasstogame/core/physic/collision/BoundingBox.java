package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class BoundingBox {


    BoundingBoxType type;
    Vector2d position;
    Box shape;
    double diam1;
    double diam2;
    Vector2d[] points;

    public void update(GameObject go) {
        position.x = go.position.x;
        position.y = go.position.y;
        switch (type) {
            case RECTANGLE:
                shape.update(go);
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
        }
        return result;
    }

    public enum BoundingBoxType {
        POINT,
        RECTANGLE,
        CIRCLE,
        ELLIPSE;
    }

}
