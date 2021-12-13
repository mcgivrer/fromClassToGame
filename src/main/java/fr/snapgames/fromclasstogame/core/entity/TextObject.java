package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.awt.*;

import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class TextObject extends GameObject {

    public String text;
    public Font font;

    @Deprecated
    public TextObject(String name, double x, double y) {
        super(name, new Vector2d(x, y));

        this.color = Color.WHITE;
        type = GOType.OTHER;
    }
    public TextObject(String name, Vector2d position) {
        super(name, position);
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
