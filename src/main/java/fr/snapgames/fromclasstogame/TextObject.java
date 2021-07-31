package fr.snapgames.fromclasstogame;

import java.awt.Font;

public class TextObject extends GameObject {

    public String text;
    public Font font;

    public TextObject(String name, double x, double y) {
        super(name, x, y);
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
