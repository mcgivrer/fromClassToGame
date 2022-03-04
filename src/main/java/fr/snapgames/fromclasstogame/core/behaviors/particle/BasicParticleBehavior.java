package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;

import java.awt.*;

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
    public void onCreate(Particle p) {
        Behavior.super.onCreate(p);
        p.alive = true;
        p.life = defaultLifeDuration;
        p.color = defaultColor;
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
     * @param go the particle to be impacted by user input.
     * @param ih the ActionHandler providing input feedback.
     */
    @Override
    public void onInput(Particle go, ActionHandler ih) {

    }

    /**
     * Compute the life and physic animation  for the particle.
     *
     * @param go the particle to be updated
     * @param dt the elapsed time since previous call.
     */
    @Override
    public void onUpdate(Particle go, long dt) {
        if (go.alive) {

            if (go.life - dt >= 0) {
                go.life -= dt;
            } else {
                go.life = -1;
                go.alive = false;
            }
        }
        double time = Utils.range(dt * 0.125, 0, 16);
        go.acceleration.multiply(time * 0.5);
        go.velocity.add(go.acceleration);
        go.position.add(go.velocity);

    }

    @Override
    public void onRender(Particle go, Renderer r) {
        // Nothing special to do
    }

    @Override
    public void onAction(Particle go, Integer action) {

    }
}
