package fr.snapgames.fromclasstogame.demo.entity;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class LifeObject extends GameObject {
    public int value = 0;

    @Deprecated
    public LifeObject(String name, double x, double y) {
        super(name, new Vector2d(x, y));
        this.width = 24;
        this.height = 18;

    }

    public LifeObject(String name, Vector2d position) {
        super(name, position);
    }

    public LifeObject setLive(int value) {
        this.value = value;
        this.width = 24;
        this.height = 18;
        return this;
    }
}
