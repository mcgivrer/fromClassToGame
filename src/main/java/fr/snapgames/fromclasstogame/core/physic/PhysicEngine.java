package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.entity.GameObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicEngine {

    public Game game;

    public World world;

    public List<GameObject> objects = new ArrayList<>();

    public PhysicEngine(Game g) {
        this.game = g;
    }

    public void update(long dt) {
        for (GameObject go : objects) {
            update(go, dt);
        }
    }

    private void update(GameObject go, long dt) {
        if (!go.relativeToCamera) {
            double friction = go.material != null ? go.material.staticFriction : 1;
            go.dx = go.dx * friction;
            double gravity = world != null ? world.gravity : 0;
            go.dy = (go.dy + go.gravity + (gravity * 0.11)) * friction * 1 / go.mass;

            go.x += go.dx * dt;
            go.y += go.dy * dt;
            verifyGameConstraint(go);
        }
    }

    private void verifyGameConstraint(GameObject go) {
        double bounciness = go.material != null ? go.material.bounciness : 0.0;
        if (go.x < 0) {
            go.x = 0;
            go.dx = -go.dx * bounciness;
        }
        if (go.y < 0) {
            go.y = 0;
            go.dy = -go.dy * bounciness;
        }
        if (go.x + go.width > world.width) {
            go.x = world.width - go.width;
            go.dx = -go.dx * bounciness;
        }
        if (go.y + go.height > world.height) {
            go.y = world.height - go.height;
            go.dy = -go.dy * bounciness;
        }
    }

    public void add(GameObject go) {
        this.objects.add(go);
    }

    public World getWorld() {
        return world;
    }

    public PhysicEngine setWorld(World w) {
        this.world = w;
        return this;
    }
}
