package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {

    public Game game;

    public List<GameObject> objects = new ArrayList<>();

    public double width;
    public double height;

    public double gravity = 0.981;

    public World(Game g, double width, double height) {
        this.game = g;
        this.width = width;
        this.height = height;
    }

    public void update(long dt) {
        for (GameObject go : objects) {
            update(go, dt);
        }
    }

    private void update(GameObject go, long dt) {
        if (!go.relativeToCamera) {
            go.x += go.dx * dt;
            go.y += (go.dy + go.gravity + (gravity*0.11)) * dt;
            verifyGameConstraint(go);
        }
    }

    private void verifyGameConstraint(GameObject go) {
        if (go.x < 0) {
            go.x = 0;
            if (go.material != null) {
                go.dx = -go.dx * go.material.bouncyness;
            }
        }
        if (go.y < 0) {
            go.y = 0;
            if (go.material != null) {
                go.dy = -go.dy * go.material.bouncyness;
            }
        }
        if (go.x + go.width > width) {
            go.x = width - go.width;
            if (go.material != null) {
                go.dx = -go.dx * go.material.bouncyness;
            }
        }
        if (go.y + go.height > height) {
            go.y = height - go.height;
            if (go.material != null) {
                go.dy = -go.dy * go.material.bouncyness;
            }
        }
    }

    public void add(GameObject go) {
        this.objects.add(go);
    }

}
