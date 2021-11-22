package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;

import java.awt.*;

public class BasicParticleBehavior implements Behavior<Particle> {

    /**
     * Parent particle System
     */
    private ParticleSystem parent;
    /**
     * Default life duration initialisation
     */
    private long defaultLifeDuration;
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
        p.color = Color.WHITE;
        p.setSize(1 + (int) (Math.random() * 2));
        p.setPosition(parent.position);
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

    @Override
    public void onInput(Particle go, ActionHandler ih) {

    }

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
        double time = (double) dt / 1000.0;
        //go.velocity.add(go.acceleration).multiply(time);
        go.position = go.position.add(go.velocity.multiply(time));
    }

    @Override
    public void onRender(Particle go, Render r) {
        // Nothing special to do
    }

    @Override
    public void onAction(Particle go, ActionHandler.ACTIONS action) {
        // Nothing special to do
    }
}
