package fr.snapgames.fromclasstogame.demo.entity;

import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.awt.*;

public class ScoreObject extends TextObject {

    private int score;

    @Deprecated
    public ScoreObject(String name, double x, double y) {
        super(name, x, y);
        score = 0;
        this.color = Color.WHITE;
    }

    public ScoreObject(String name, Vector2d position) {
        super(name, position);
        score = 0;
        this.color = Color.WHITE;
    }

    public ScoreObject setScore(int s) {
        score = s;
        text = String.format("%06d", score);
        return this;
    }

    @Override
    public void update(long dt) {
        super.update(dt);
        this.width = text.length() * 8;
        this.height = 16;
    }

}
