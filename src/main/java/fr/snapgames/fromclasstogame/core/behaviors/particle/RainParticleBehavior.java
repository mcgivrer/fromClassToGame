package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.physic.World;

/**
 * The `RainParticleBehavior` is a specific behavior ro animate rain drop on screen (relative to camera) according to
 * camera position, world viewport size and world wind.
 */
public class RainParticleBehavior extends BasicParticleBehavior {

    /**
     * This will be soon the entry point to make the sky rain.
     *
     * @param ps     The Particle System to be animated
     * @param world  the world where thing happened
     * @param camera the camera to focus rain drop animation to.
     */
    public RainParticleBehavior(ParticleSystem ps, World world, Camera camera) {
        super(ps, -1, true);
        parent.setRelativeToCamera(true);

        // compute window rendering view for rain

    }

    /**
     * Here will be generated the fresh drop from top of the sky, ready to reach the floor.
     *
     * @param drop the drop to be initialized.
     */
    @Override
    public void onCreate(Particle drop) {
        super.onCreate(drop);

        // randomly set horizontal rain drop position on World viewport, and in camera view.

    }

    /**
     * Animation of the drop from sky to floor, applying the world effects and keeping visible
     * animated things in the camera viewport.
     *
     * @param drop the drop to be animated
     * @param dt   the elapsed time since previous call.
     */
    @Override
    public void onUpdate(Particle drop, long dt) {
        super.onUpdate(drop, dt);
        // Define Rain rendering window

        // set Rain orientation according to Wind Vector2d in world

        // update rain drop

        // test if rain drop is out of World, restart rain drop by create it again.

    }

}
