package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.system.System;

import java.util.ArrayList;

public class PhysicEngine extends System {

    public World world;

    @Override
    public String getName() {
        return "PhysicEngine";
    }

    public PhysicEngine(Game g) {
        super(g);
    }

    @Override
    public int initialize(Configuration config) {
        objects = new ArrayList<>();
        return 1;
    }

    @Override
    public void dispose() {
        objects.clear();
    }

    public PhysicEngine setWorld(World w) {
        this.world = w;
        return this;
    }

    public void update(long dt) {
        for (GameObject go : objects) {
            update(go, dt);
        }
    }

    private void update(GameObject go, long dt) {
        if (!go.relativeToCamera) {
            go.dx = go.dx * go.material.staticFriction;
            go.dy = (go.dy + go.gravity + (world.gravity * 0.11))
                    * go.material.staticFriction * 1 / go.mass;

            go.x += go.dx * dt;
            go.y += go.dy * dt;
            verifyGameConstraint(go);

            if (go.bbox != null) {
                go.bbox.update(go);
            }
        }
    }

    private void verifyGameConstraint(GameObject go) {
        if (go.x < 0) {
            go.x = 0;
            if (go.material != null) {
                go.dx = -go.dx * go.material.bounciness;
            }
        }
        if (go.y < 0) {
            go.y = 0;
            if (go.material != null) {
                go.dy = -go.dy * go.material.bounciness;
            }
        }
        if (go.x + go.width > world.width) {
            go.x = world.width - go.width;
            if (go.material != null) {
                go.dx = -go.dx * go.material.bounciness;
            }
        }
        if (go.y + go.height > world.height) {
            go.y = world.height - go.height;
            if (go.material != null) {
                go.dy = -go.dy * go.material.bounciness;
            }
        }
    }

    public void add(GameObject go) {
        this.objects.add(go);
    }

    public World getWorld() {
        return world;
    }
}
