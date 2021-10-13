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
        world = new World(config.width, config.height);
        world.setGravity(config.gravity);
        return 1;
    }

    @Override
    public void dispose() {
        objects.clear();
    }

    public void update(long dt) {
        for (GameObject go : objects) {
            update(go, dt);
        }
    }

    private void update(GameObject go, long dt) {
        if (!go.relativeToCamera) {

            // Acceleration is not already used in velocity & position computation
            double friction = go.material != null ? go.material.staticFriction : 1;
            go.velocity.x = go.velocity.x * friction;
            double gravity = world != null ? world.gravity : 0;
            go.velocity.y = (go.velocity.y + go.gravity + (gravity * 0.11)) * friction * 1 / go.mass;

            go.position.x += go.velocity.x * dt;
            go.position.y += go.velocity.y * dt;

            verifyGameConstraint(go);

            if (go.bbox != null) {
                go.bbox.update(go);
            }
        }
    }

    private void verifyGameConstraint(GameObject go) {
        double bounciness = go.material != null ? go.material.bounciness : 0.0;
        if (go.position.x < 0) {
            go.position.x = 0;
            go.velocity.x = -go.velocity.x * bounciness;
        }
        if (go.position.y < 0) {
            go.position.y = 0;
            go.velocity.y = -go.velocity.y * bounciness;
        }
        if (go.position.x + go.width > world.width) {
            go.position.x = world.width - go.width;
            go.velocity.x = -go.velocity.x * bounciness;
        }
        if (go.position.y + go.height > world.height) {
            go.position.y = world.height - go.height;
            go.velocity.y = -go.velocity.y * bounciness;
        }
    }

    public void addToPipeline(GameObject go) {
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
