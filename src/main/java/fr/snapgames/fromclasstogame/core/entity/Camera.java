package fr.snapgames.fromclasstogame.core.entity;

import java.awt.Dimension;

/**
 * <p>The Camera will follow a target centered in its viewport.</p>
 * <p>The camera moves are computed according to the tween spring factor.</p>
 */
public class Camera extends GameObject {
    GameObject target;
    Dimension viewport;
    double tween;

    public Camera(String name) {
        super(name, 0, 0);
    }

    public Camera setTarget(GameObject t) {
        target = t;
        x = 0;
        y = 0;
        return this;
    }

    public Camera setViewport(Dimension v) {
        viewport = v;
        width = v.width;
        height = v.height;
        return this;
    }

    public Camera setTweenFactor(double t) {
        tween = t;
        return this;
    }

    @Override
    public void update(long dt) {
        // Adding some Math security to avoid infinite values
        this.x += Math.ceil(
                (target.x + (target.width) - ((double) (viewport.width) * 0.5f) - this.x) * tween * Math.min(dt, 10));
        this.y += Math.ceil(
                (target.y + (target.height) - ((double) (viewport.height) * 0.5f) - this.y) * tween * Math.min(dt, 10));
    }
}
