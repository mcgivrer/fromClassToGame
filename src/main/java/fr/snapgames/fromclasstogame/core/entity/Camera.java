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
        return this;
    }

    public Camera setViewport(Dimension v) {
        viewport = v;
        return this;
    }

    public Camera setTweenFactor(double t) {
        tween = t;
        return this;
    }

    @Override
    public void update(long dt) {
        x += target.x + target.width - (viewport.width * 0.5f - x) * tween * dt;
        y += target.y + target.height - (viewport.height * 0.5f - y) * tween * dt;
    }
}
