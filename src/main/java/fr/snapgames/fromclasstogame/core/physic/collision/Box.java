package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.entity.GameObject;

public class Box {
    public double x, y;
    public double width;
    public double height;

    public void update(GameObject go) {
        x = go.position.x;
        y = go.position.y;
        width = go.width;
        height = go.height;
    }

    public boolean collide(Box b) {
        return Math.abs(x - b.x) < Math.abs(width - b.width) && Math.abs(y - b.y) < Math.abs(height - b.height);
    }
}
