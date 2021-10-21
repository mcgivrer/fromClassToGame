package fr.snapgames.fromclasstogame.demo.entity;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class LifeObject extends GameObject {
    public int value = 0;

    @Deprecated
    public LifeObject(String name, double x, double y) {
        super(name, x, y);
    }

    public LifeObject(String name, Vector2d position) {
        super(name, position);
    }

    public LifeObject setLive(int value) {
        this.value = value;
        return this;
    }

}
