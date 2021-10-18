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
        double dtCorrected = dt * 0.01;
        if (!go.relativeToCamera) {

            // Acceleration is not already used in velocity & position computation
            Vector2d gravity = world != null ? world.gravity : Vector2d.ZERO;
            go.acceleration = go.acceleration.add(gravity.multiply(-1)).add(new Vector2d(0, go.mass));

            // Compute velocity
            double friction = go.material != null ? go.material.staticFriction : 1;
            double dynFriction = go.material != null ? go.material.dynFriction : 1;
            go.velocity = go.velocity.add(go.acceleration.multiply(dtCorrected).multiply(friction).multiply(dynFriction));

            // Compute position
            go.position.x += ceilMinMaxValue(go.velocity.x * dtCorrected, 0.1, world.maxVelocity);
            go.position.y += ceilMinMaxValue(go.velocity.y * dtCorrected, 0.1, world.maxVelocity);


            // test World space constrained
            verifyGameConstraint(go);

            // update Bounding box for this GameObject.
            if (go.bbox != null) {
                go.bbox.update(go);
            }
        }
    }

    private double ceilValue(double x, double ceil) {
        return Math.copySign((Math.abs(x) < ceil ? 0 : x), x);
    }

    private double ceilMaxValue(double x, double ceil) {
        return Math.copySign((Math.abs(x) > ceil ? ceil : x), x);
    }

    private double ceilMinMaxValue(double x, double min, double max) {
        return ceilValue(Math.copySign((Math.abs(x) > max ? max : x), x), min);
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
