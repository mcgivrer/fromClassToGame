package fr.snapgames.fromclasstogame.test.entity;

import fr.snapgames.fromclasstogame.core.entity.GameObject;

public class TestObject extends GameObject {
    private boolean flag;

    public TestObject(String name, double x, double y) {
        super(name, x, y);
    }

    public Boolean getFlag() {
        return flag;
    }

    public TestObject setFlag(boolean b) {
        this.flag = b;
        return this;
    }
}
