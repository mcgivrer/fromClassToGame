package fr.snapgames.fromclasstogame.demo.scenes;

import fr.snapgames.fromclasstogame.core.entity.TextObject;

public class ScoreObject extends TextObject {

    private int score;

    public ScoreObject(String name, double x, double y) {
        super(name, x, y);
        score = 0;
    }

    public ScoreObject setScore(int s) {
        score = s;
        text = String.format("%06d", score);
        return this;
    }

}
