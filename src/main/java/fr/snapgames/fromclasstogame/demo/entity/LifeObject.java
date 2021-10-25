package fr.snapgames.fromclasstogame.demo.entity;

import fr.snapgames.fromclasstogame.core.entity.GameObject;

public class LifeObject extends GameObject {
    public int value = 0;

    public LifeObject(String name, double x, double y) {
        super(name, x, y);
    }

    public LifeObject setLive(int value) {
        this.value = value;
        return this;
    }

}
