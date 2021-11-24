package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.physic.World;

import java.awt.*;

/**
 * The `RainParticleBehavior` is a specific behavior ro animate rain drop on screen (relative to camera) according to
 * camera position, world viewport size and world wind.
 */
public class RainParticleBehavior extends BasicParticleBehavior {

    public RainParticleBehavior(ParticleSystem ps, World world, Camera camera) {
        super(ps, -1, true);
        parent.relativeToCamera(true);

        // compute window rendering view for rain

    }

    @Override
    public void onCreate(Particle drop) {
        super.onCreate(drop);

        // randomly set horizontal rain drop position on World viewport, and in camera view.

    }

    @Override
    public void onUpdate(Particle drop, long dt) {
        super.onUpdate(drop, dt);
        // Define Rain rendering window

        // set Rain orientation according to Wind Vector2d in world

        // update rain drop

        // test if rain drop is out of World, restart rain drop by create it again.

    }

}
