package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class BoundingBox {

    public enum BoundingBoxType {
        POINT,
        RECTANGLE,
        CIRCLE,
        ELLIPSE;
    }

    BoundingBoxType type;
    Vector2d position;
    Box shape;
    double diam1;
    double diam2;
    Vector2d[] points;

    public void update(GameObject go) {
        position.x = go.x;
        position.y = go.y;
        switch (type) {
            case RECTANGLE:
                shape.update(go);
                break;
            default:
                break;
        }

    }
}
