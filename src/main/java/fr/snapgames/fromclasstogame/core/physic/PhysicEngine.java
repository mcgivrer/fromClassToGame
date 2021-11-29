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

    private World world = new World(0, 0);

    public PhysicEngine(Game g) {
        super(g);
    }

    @Override
    public String getName() {
        return "PhysicEngine";
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
            if (!game.isPause()) {
                for (GameObject go : getObjects()) {
                    update(go, dt);
                }
            }
        } catch (ConcurrentModificationException e) {
            logger.error("Unable to update the GameObjects");
        }
    }

    private void update(GameObject go, long dt) {
        double dtCorrected = dt * 0.01;
        if (go != null) {
            go.acceleration = new Vector2d();

            if (!go.relativeToCamera) {

                // update the bounding box for this GameObject.
                go.bbox.update(go);

                // Acceleration is not already used in velocity & position computation
                Vector2d gravity = world != null ? world.gravity : Vector2d.ZERO;
                // Apply World influence
                Vector2d massAppliedToGravity = new Vector2d();
                massAppliedToGravity.add(gravity).multiply(go.mass);
                go.forces.add(massAppliedToGravity);


                Vector2d acc = applyInfluences(go);
                for (Vector2d f : go.forces) {
                    acc.add(f);
                }

                go.acceleration.add(acc);


                // limit acceleration with GameObject threshold `maxHorizontalAcceleration` and `maxVerticalAcceleration`
                applyMaxThreshold(go, "maxHorizontalAcceleration", "maxVerticalAcceleration", go.acceleration);

                // Compute velocity
                double friction = go.material != null ? go.material.staticFriction : 1;
                go.velocity = go.velocity.add(go.acceleration.multiply(dtCorrected)).multiply(friction);

                // if the GameObject is touching anything, apply some friction !
                boolean touching = (boolean) go.getAttribute("touching", false);
                if (touching && Math.abs(go.acceleration.x) < 0.5 && Math.abs(go.acceleration.y) < 0.5) {
                    double dynFriction = go.material != null ? go.material.dynFriction : 1;
                    go.velocity = go.velocity.multiply(dynFriction);
                }

                // limit velocity with GameObject threshold `maxHorizontalVelocity` and `maxVerticalVelocity`
                applyMaxThreshold(go, "maxHorizontalVelocity", "maxVerticalVelocity", go.velocity);
                // Compute position
                go.position.x += ceilMinMaxValue(go.velocity.x * dtCorrected, 0.1, world.maxVelocity);
                go.position.y += ceilMinMaxValue(go.velocity.y * dtCorrected, 0.1, world.maxVelocity);

                // apply Object behaviors computations
                if (go.behaviors.size() > 0) {
                    go.behaviors.forEach(b -> b.onUpdate(go, dt));
                }

                // test World space constrained
                verifyGameConstraint(go);
                // update Bounding box for this GameObject.
                if (go.bbox != null) {
                    go.bbox.update(go);
                }
                go.forces.clear();
            }
            // Update the Object itself
            go.update(dt);
        }
    }

    private void applyMaxThreshold(GameObject go, String maxHorizontalThreshold, String maxVerticalThreshold, Vector2d acceleration) {
        if (go.getAttributes().containsKey(maxHorizontalThreshold)) {
            double ax = (Double) go.getAttribute(maxHorizontalThreshold, 0);
            acceleration.x = Math.abs(acceleration.x) > ax ? Math.signum(acceleration.x) * ax : acceleration.x;
        }
        if (go.getAttributes().containsKey(maxVerticalThreshold)) {
            double ay = (Double) go.getAttribute(maxVerticalThreshold, 0);
            acceleration.y = Math.abs(acceleration.y) > ay ? Math.signum(acceleration.y) * ay : acceleration.y;
        }
    }

    /**
     * Apply World influence Area to the {@link GameObject} <code>go</code>.
     *
     * @param go the {@link GameObject} to
     */
    private Vector2d applyInfluences(GameObject go) {
        Vector2d acc = new Vector2d();
        if (world.influenceAreas.size() > 0 && !go.relativeToCamera) {
            for (InfluenceArea2d area : world.influenceAreas) {
                if (area.influenceArea.intersect(go.bbox)) {
                    double influence = area.getInfluenceAtPosition(go.position);
                    Vector2d accIA = new Vector2d();
                    accIA.add(area.force).multiply(influence).multiply(area.energy);
                    acc.add(accIA);
                }
            }

            // Update the Object itself
            go.update(dt);
        }
        return acc;
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
