package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.awt.*;

public class LightObject extends GameObject {
    public Color foregroundColor;
    public Color[] colors;
    public Double intensity;
    public Double glitterEffect;
    public float[] dist;
    public RadialGradientPaint rgp;
    public LightType lightType;

    public LightObject(String name, Vector2d pos, LightType type) {
        super(name, pos);
        setType(type);
    }

    public LightObject setType(LightType type) {
        this.lightType = type;
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
}
