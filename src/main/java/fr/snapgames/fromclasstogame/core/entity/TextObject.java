package fr.snapgames.fromclasstogame.core.entity;

import java.awt.*;

public class TextObject extends GameObject {

    public String text;
    public Font font;

    public TextObject(String name, double x, double y) {
        super(name, x, y);
        this.color = Color.WHITE;
        type = GOType.OTHER;
    }

    public TextObject setText(String text) {
        this.text = text;
        return this;
    }
    public TextObject setFont(Font f) {
        this.font = f;
        return this;
    }

}
