package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * <p>
 * The {@link PhysicEngine} is the heart of move in te game framework we intend
 * to develop here.
 * </p>
 *
 * <p>
 * As a {@link System} itself, it handles a list of {@link GameObject} to
 * animate and move according to their
 * own physic characteristics and rules, using {@link Material} to support those
 * characteristics.
 * </p>
 *
 * <p>
 * Material is attached to a GameObject, and World provide constraints
 * ({@link InfluenceArea2d} applied to all objects
 * managed by the PhysicEngine.
 * </p>
 * <p>
 * By the way, the gravity (if necessary) is provided by the {@link World}
 * object.
 * </p>
 *
 * <p>
 * Usage of this system is as other, Add it to the
 * {@link fr.snapgames.fromclasstogame.core.system.SystemManager} at Game
 * initialization,
 * and you just ahe to add a GameObject to the SystemManager, it will be
 * automatically supported by.
 * </p>
 *
 * <pre>
 *     // At game initialization:
 *     SystemManager.add(new PhysicEngine(game));
 *
 *     // At Scene creation
 *     PhysicEngine pe = (PhysicEngine)SystemManager.get(PhysicEngine.class);
 *     GameObject go = new GameObject("myObj",new Vector2d(100,200))
 *          .setMaterial(DefaultMaterial.ROCK.get());
 *     pe.add(go)
 *
 *     // In the game update process
 *     pe.update(dt)
 *     // Note: long dt is the elapsed time since previous call.
 * </pre>
 *
 * @author Frédéric Delorme
 * @see World
 * @see System
 * @see Material
 * @see GameObject
 * @since 1.0.0
 */
public class PhysicEngine extends System {
    private static final Logger logger = LoggerFactory.getLogger(PhysicEngine.class);

    /**
     * The current World object managed by the PhysicEngine.
     */
    private World world = new World(0, 0);

    /**
     * Initialization of the {@link PhysicEngine} System with its parent
     * {@link Game}.
     *
     * @param g the parent game using this System.
     */
    public PhysicEngine(Game g) {
        super(g);
    }

    /**
     * return the System name.
     *
     * @return
     */
    @Override
    public String getName() {
        return "PhysicEngine";
    }

    /**
     * Initialization with configuration parameters of the PhysicEngine, called by
     * SystemManager
     * during initialization phase.
     *
     * @param config the {@link Configuration} object containing properties.
     * @return 1 if intilization is OK.
     */
    @Override
    public int initialize(Configuration config) {
        objects = new ArrayList<>();
        world = new World(config.width, config.height);
        world.setGravity(config.gravity);
        return 1;
    }

    /**
     * release all resources.
     */
    @Override
    public void dispose() {
        objects.clear();
    }

    /**
     * THe heart of the system, updating all objects bu applying
     * {@link GameObject#forces} and
     * {@link World#influenceAreas} constrains to the list of managed objects. This
     * process is active until the
     * {@link Game#isPause()} state is up.
     *
     * @param dt elapsed time since previous call.
     */
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

    /**
     * Internal GameObject update according to elapsed time.
     *
     * @param go The GameObject to be updated.
     * @param dt elapsed time since previous call.
     */
    private void update(GameObject go, long dt) {
        double dtCorrected = dt * 0.01;
        if (go != null) {
            go.acceleration = new Vector2d();

            if (!go.relativeToCamera) {

                // update the bounding box for this GameObject.
                go.bbox.update(go);

                // Acceleration is not already used in velocity & position computation
                computeAcceleration(go);

                // Compute velocity
                computeVelocity(go, dtCorrected);
                // Compute position
                go.position.x += ceilMinMaxValue(go.velocity.x * dtCorrected, 0.1, world.maxVelocity);
                go.position.y += ceilMinMaxValue(go.velocity.y * dtCorrected, 0.1, world.maxVelocity);

                // test World space constrained
                verifyGameConstraint(go);


                // update Bounding box for this GameObject.
                if (go.bbox != null) {
                    go.bbox.update(go);
                }
                go.forces.clear();
            }

            // apply Object behaviors computations
            if (!go.behaviors.isEmpty()) {
                go.behaviors.forEach(b -> b.onUpdate(go, dt));
            }
            // Update the Object itself
            go.update(dt);
        }
    }

    /**
     * Compute velocity for the {@link GameObject} go, applying a dtCorrected
     *
     * @param go          The GameObject to compute velocity.
     * @param dtCorrected the corrected elapsed time
     */
    private void computeVelocity(GameObject go, double dtCorrected) {
        double friction = go.material != null ? go.material.staticFriction : 1;
        go.velocity = go.velocity.add(go.acceleration.multiply(dtCorrected)).multiply(friction);

        // if the GameObject is touching anything, apply some friction !
        boolean touching = (boolean) go.getAttribute("touching", false);
        if (touching && Math.abs(go.acceleration.x) < 0.5 && Math.abs(go.acceleration.y) < 0.5) {
            double dynFriction = go.material != null ? go.material.dynFriction : 1;
            go.velocity = go.velocity.multiply(dynFriction);
        }

        // limit velocity with GameObject threshold `maxHorizontalVelocity` and
        // `maxVerticalVelocity`
        applyMaxThreshold(go, "maxHorizontalVelocity", "maxVerticalVelocity", go.velocity);
    }

    /**
     * Compute the acceleration to be applied to {@link GameObject} go.
     *
     * @param go The GameObject to compute velocity.
     */
    private void computeAcceleration(GameObject go) {
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

        // limit acceleration with GameObject threshold `maxHorizontalAcceleration` and
        // `maxVerticalAcceleration`
        applyMaxThreshold(go, "maxHorizontalAcceleration", "maxVerticalAcceleration", go.acceleration);
    }

    /**
     * Apply max and min thresold to the computed values to set value limits.
     *
     * @param go                     The Game obejct to apply threshold on
     * @param maxHorizontalThreshold the max horizontal threshold attribute name for
     *                               this object
     * @param maxVerticalThreshold   the max vertical threshold attribute name for
     *                               this object
     * @param acceleration           the acceleration to be applied.
     */
    private void applyMaxThreshold(GameObject go, String maxHorizontalThreshold, String maxVerticalThreshold,
                                   Vector2d acceleration) {
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
     * @param go the {@link GameObject} to apply constraint and computation to.
     */
    private Vector2d applyInfluences(GameObject go) {
        Vector2d acc = new Vector2d();
        if (!world.influenceAreas.isEmpty() && !go.relativeToCamera) {
            for (InfluenceArea2d area : world.influenceAreas) {
                if (area.influenceArea.intersect(go.bbox)) {
                    double influence = area.getInfluenceAtPosition(go.position);
                    Vector2d accIA = new Vector2d();
                    accIA.add(area.force).multiply(influence).multiply(area.energy);
                    acc.add(accIA);
                }
            }
        }
        return acc;
    }

    /**
     * toolbox to define and fix ceil value
     *
     * @param x    the value to "ceilled"
     * @param ceil the level of ceil to apply to x value.
     * @return value with ceil applied.
     */
    private double ceilValue(double x, double ceil) {
        return Math.copySign((Math.abs(x) < ceil ? 0 : x), x);
    }

    /**
     * min-max-range to apply to a x value.
     *
     * @param x   the value to be constrained between min and max.
     * @param min minimum for the x value.
     * @param max maximum for the x value.
     * @return
     */
    private double ceilMinMaxValue(double x, double min, double max) {
        return ceilValue(Math.copySign((Math.abs(x) > max ? max : x), x), min);
    }

    /**
     * Constrain {@link GameObject} to not be out of the {@link World} area.
     *
     * @param go the {@link GameObject} to be constrained to the game {@link World}.
     */
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

        // Touching ?
        if (go.position.y + go.height >= world.height) {
            go.addAttribute("touching", true);
            go.addAttribute("jumping", false);
        }
    }

    /**
     * return {@link World} defined in the {@link PhysicEngine}.
     *
     * @return the PhysicEngine {@link World} object
     */
    public World getWorld() {
        return world;
    }

    /**
     * define the {@link World} to be applied to the {@link PhysicEngine} GameObject
     * processing.
     *
     * @param w the {@link World} object to be used in {@link GameObject}
     *          computations
     * @return the {@link PhysicEngine} itself updated with the new {@link World}
     * defined.
     */
    public PhysicEngine setWorld(World w) {
        this.world = w;
        return this;
    }
}
