package fr.snapgames.fromclasstogame.demo.entity;

import fr.snapgames.fromclasstogame.core.entity.TextObject;

import java.awt.*;

public class TextValueObject extends TextObject {

    private int value;

    public TextValueObject(String name, double x, double y) {
        super(name, x, y);
        value = 0;
        this.color = Color.WHITE;
    }

    public TextValueObject setValue(int v) {
        value = v;
        text = String.format("%d", value);
        return this;
    }
}
