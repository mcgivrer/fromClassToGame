package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;

import java.awt.*;

/**
 * Particle animation processing from create to update.
 *
 * @author Frédéric Delorme
 * @since 1.0.2
 */
public class BasicParticleBehavior implements Behavior<Particle> {

    /**
     * Parent particle System
     */
    protected ParticleSystem parent;
    /**
     * Default life duration initialisation
     */
    protected long defaultLifeDuration;
    /**
     * Default color for particle ar initialization
     */
    private Color defaultColor = Color.WHITE;

    public BasicParticleBehavior(ParticleSystem ps, int defaultLifeTimeMS, boolean restart) {
        super();
        this.parent = ps;
        this.defaultLifeDuration = defaultLifeTimeMS;
        ps.setRestart(restart);
    }

    @Override
    public void onCreate(Particle entity) {
        Behavior.super.onCreate(entity);
        entity.alive = true;
        entity.life = defaultLifeDuration;
        entity.color = defaultColor;
    }

    /**
     * Define the default Color for the new particle
     *
     * @param c color to be set.
     * @return the Modified BasicParticleBehavior object
     */
    public BasicParticleBehavior setColor(Color c) {
        this.defaultColor = c;
        return this;
    }

    /**
     * Set the default lifetime t for the new particle.
     *
     * @param t is the default life duration for this particle
     * @return the modified `BasicParticleBehavior` object
     */
    public BasicParticleBehavior setLifeDuration(int t) {
        this.defaultLifeDuration = t;
        return this;
    }

    /**
     * The only purpose is to provide a default implementation for any Behavior that will take in account user input.
     *
     * @param entity the particle to be impacted by user input.
     * @param ah     the ActionHandler providing input feedback.
     */
    @Override
    public void onInput(Particle entity, ActionHandler ah) {

    }

    /**
     * Compute the life and physic animation  for the particle.
     *
     * @param entity  the particle to be updated
     * @param elapsed the elapsed time since previous call.
     */
    @Override
    public void onUpdate(Particle entity, long elapsed) {
        if (entity.alive) {

            if (entity.life - elapsed >= 0) {
                entity.life -= elapsed;
            } else {
                entity.life = -1;
                entity.alive = false;
            }
        }
        double time = Utils.range(elapsed * 0.125, 0, 16);
        entity.acceleration.multiply(time * 0.5);
        entity.velocity.add(entity.acceleration);
        entity.position.add(entity.velocity);

    }

    @Override
    public void onRender(Particle go, Render render) {
        // Nothing special to do
    }

    @Override
    public void onAction(Particle entity, Integer action) {

    }
}
