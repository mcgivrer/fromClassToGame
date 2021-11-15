package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class PhysicEngine extends System {
    private static final Logger logger = LoggerFactory.getLogger(PhysicEngine.class);

    private World world;

    public PhysicEngine(Game g) {
        super(g);
    }

    @Override
    public String getName() {
        return PhysicEngine.class.getName();
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
        try {
            for (GameObject go : getObjects()) {
                update(go, dt);
            }
        } catch (ConcurrentModificationException e) {
            logger.error("Unable to update the GameObjects");
        }
    }

    private void update(GameObject go, long dt) {
        double dtCorrected = dt * 0.01;
        if (go != null && !go.relativeToCamera) {

            boolean touching = (boolean) go.getAttribute("touching", false);
            // Acceleration is not already used in velocity & position computation
            Vector2d gravity = world != null ? world.gravity : Vector2d.ZERO;
            go.acceleration = go.acceleration.add(gravity).add(new Vector2d(0, go.mass));

            // limit acceleration with GameObject threshold `maxHorizontalAcceleration` and `maxVerticalAcceleration`
            if (go.getAttributes().containsKey("maxHorizontalAcceleration")) {
                double ax = (Double) go.getAttribute("maxHorizontalAcceleration",0);
                go.acceleration.x = Math.abs(go.acceleration.x) > ax ? Math.signum(go.acceleration.x) * ax : go.acceleration.x;
            }
            if (go.getAttributes().containsKey("maxVerticalAcceleration")) {
                double ay = (Double) go.getAttribute("maxVerticalAcceleration",0);
                go.acceleration.y = Math.abs(go.acceleration.y) > ay ? Math.signum(go.acceleration.y) * ay : go.acceleration.y;
            }

            // Compute velocity
            double friction = go.material != null ? go.material.staticFriction : 1;
            go.velocity = go.velocity.add(go.acceleration.multiply(dtCorrected)).multiply(friction);

            if (touching && Math.abs(go.acceleration.x) < 0.5 && Math.abs(go.acceleration.y) < 0.5) {
                double dynFriction = dynFriction = go.material != null ? go.material.dynFriction : 1;
                go.velocity = go.velocity.multiply(dynFriction);
            }

            // limit velocity with GameObject threshold `maxHorizontalVelocity` and `maxVerticalVelocity`
            if (go.getAttributes().containsKey("maxHorizontalVelocity")) {
                double dx = (Double) go.getAttribute("maxHorizontalVelocity",0);
                go.velocity.x = Math.abs(go.velocity.x) > dx ? Math.signum(go.velocity.x) * dx : go.velocity.x;
            }
            if (go.getAttributes().containsKey("maxVerticalVelocity")) {
                double dy = (Double) go.getAttribute("maxVerticalVelocity",0);
                go.velocity.y = Math.abs(go.velocity.y) > dy ? Math.signum(go.velocity.y) * dy : go.velocity.y;
            }
            // Compute position
            go.position.x += ceilMinMaxValue(go.velocity.x * dtCorrected, 0.1, world.maxVelocity);
            go.position.y += ceilMinMaxValue(go.velocity.y * dtCorrected, 0.1, world.maxVelocity);

            // Update the Object itself
            go.update(dt);
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

    private double ceilMinMaxValue(double x, double min, double max) {
        return ceilValue(Math.copySign((Math.abs(x) > max ? max : x), x), min);
    }

    private void verifyGameConstraint(GameObject go) {
        double bounciness = go.material != null ? go.material.bounciness : 0.0;
        go.addAttribute("touching", false);
        if (go.position.x < 0) {
            go.position.x = 0;
            go.velocity.x = -go.velocity.x * bounciness;
        }
        if (go.position.x + go.width >= world.width) {
            go.position.x = world.width - go.width;
            go.velocity.x = -go.velocity.x * bounciness;
        }
        if (go.position.y < 0) {
            go.position.y = 0;
            go.velocity.y = -go.velocity.y * bounciness;
        }
        if (go.position.y + go.height >= world.height) {
            go.position.y = world.height - go.height;
            go.velocity.y = -go.velocity.y * bounciness;
        }

        //Touching ?
        if (go.position.y + go.height >= world.height) {
            go.addAttribute("touching", true);
            go.addAttribute("jumping", false);
        }
    }

    public World getWorld() {
        return world;
    }

    public PhysicEngine setWorld(World w) {
        this.world = w;
        return this;
    }
}
