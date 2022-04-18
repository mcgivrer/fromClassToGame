package fr.snapgames.fromclasstogame.demo.entity;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.PEType;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class LifeObject extends GameObject {
    public int value = 0;

    @Deprecated
    public LifeObject(String name, double x, double y) {
        this(name, new Vector2d(x, y));
    }

    public LifeObject(String name, Vector2d position) {
        super(name, position);
        this.width = 24;
        this.height = 18;
        physicType = PEType.STATIC;
    }

    public LifeObject setLive(int value) {
        this.value = value;
        this.width = 24;
        this.height = 18;
        return this;
    }
}
