package fr.snapgames.fromclasstogame.demo.entity;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class LifeObject extends GameObject {
    public int value = 0;

    public LifeObject(String name, double x, double y) {
        super(name, Vector2d.ZERO);

    }

    public LifeObject setLive(int value) {
        this.value = value;
        return this;
    }

}
