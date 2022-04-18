package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

import java.awt.*;
import java.util.List;

/**
 * <p>
 * The `LightObject` will be used to simulate lights on the scene rendering.
 * </p>
 * <p>
 * To define a `LightObject`, you must set its name, its initial position, and
 * its type. and then set some of its parameters
 * </p>
 * <ul>
 * <li><code>{@link LightObject#lightType}</code> the type of light (see
 * {@link LightType})</li>
 * <li><code>{@link LightObject#setForegroundColor(Color)}</code> the light
 * color , a {@link Color}</li>
 * <li><code>{@link LightObject#setIntensity(Double)}</code> the light intensity
 * , a double value from 0.0 to 1.0)</li>
 * <li><code>{@link LightObject#setGlitterEffect(Double)} the glitter factor, a double value from 0.0 to 1.0</code></li>
 * </ul>
 *
 * @author Frédéric Delorme
 * @since 1.0.2
 */
public class LightObject extends GameObject {
    public LightType lightType;
    public Color foregroundColor;
    public Double intensity;
    public Double glitterEffect;

    public Vector2d target;

    public Color[] colors;
    public float[] dist;
    public RadialGradientPaint rgp;

    public LightObject(String name, Vector2d pos, LightType type) {
        super(name, pos);
        setType(type);
        setDebug(3);
    }

    public LightObject setType(LightType type) {
        this.lightType = type;
        if (type.equals(LightType.LIGHT_SPOT) || type.equals(LightType.LIGHT_SPHERE)) {
            this.objectType = GOType.CIRCLE;
        } else {
            this.objectType = GOType.RECTANGLE;
        }
        return this;
    }

    public LightObject setDistances(float[] dist) {
        this.dist = dist;
        return this;
    }

    public LightObject setForegroundColor(Color fgc) {
        this.foregroundColor = fgc;
        return this;
    }

    public LightObject setIntensity(Double i) {
        this.intensity = i;
        return this;
    }

    public LightObject setGlitterEffect(Double ge) {
        this.glitterEffect = ge;
        return this;
    }

    public LightObject setTarget(Vector2d target) {
        this.target = target;
        return this;
    }

    @Override
    public void update(long dt) {
        super.update(dt);
        box.update(this, new Vector2d(-width * 0.5, -height * 0.5));
    }

    @Override
    public List<String> getDebugInfo() {
        List<String> list = super.getDebugInfo();
        list.add(String.format("light:%s", this.lightType));
        return list;
    }
}
