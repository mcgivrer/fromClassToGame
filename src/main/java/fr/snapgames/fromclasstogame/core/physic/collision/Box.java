package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.entity.AbstractEntity;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;


/**
 * A Box is simple rectangle defined by its position (x,y) and its size (width,height).
 *
 * @author Frédéric Delorme
 */
public class Box {
    public double x, y;
    public double width;
    public double height;

    /**
     * Create an empty Box.
     */
    public Box() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }


    /**
     * Create a Box at `pos` with size of `width` x `height`.
     *
     * @param pos    position of the box
     * @param width  width of the box
     * @param height height of the box.
     */
    public Box(Vector2d pos, double width, double height) {
        this.x = pos.x;
        this.y = pos.y;
        this.width = width;
        this.height = height;
    }


    /**
     * Update the box according to the {@link GameObject}.
     *
     * @param go the GameObject pos and size to be cloned to.
     */
    public void update(AbstractEntity go) {
        x = go.position.x;
        y = go.position.y;
        width = go.width;
        height = go.height;
    }

    /**
     * Update the box according to the {@link GameObject}.
     *
     * @param go the GameObject pos and size to be cloned to.
     */
    public void update(GameObject go, Vector2d offset) {
        x = go.position.x + offset.x;
        y = go.position.y + offset.y;
        width = go.width;
        height = go.height;
    }

    /**
     * Define if a Box is colliding with an other <code>b</code> {@link Box}.
     *
     * @param b the boc to be tested with
     * @return true if colliding.
     */
    public boolean collide(Box b) {
        return Math.abs(x - b.x) < Math.abs(width - b.width) && Math.abs(y - b.y) < Math.abs(height - b.height);
    }
}
