package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;

import java.awt.*;

public class BasicParticleBehavior implements Behavior<Particle> {

    protected ParticleSystem parent;
    protected long defaultLifeTime;
    protected Color defaultColor = Color.WHITE;

    public BasicParticleBehavior(ParticleSystem ps, int defaultLifeTimeMS, boolean restart) {
        super();
        this.parent = ps;
        this.defaultLifeTime = defaultLifeTimeMS;
        ps.setRestart(restart);
    }

    @Override
    public void onCreate(Particle p) {
        Behavior.super.onCreate(p);
        p.alive = true;
        p.life = defaultLifeTime;
        p.color = defaultColor;
    }

    /**
     * Define the default Color for the new particle
     *
     * @param c the Color to be assigned to the particle
     * @return this BasicParticleBehavior
     */
    public BasicParticleBehavior setColor(Color c) {
        this.defaultColor = c;
        return this;
    }

    /**
     * Set the default lifetime t for the new particle.
     *
     * @param t the life duration time for this particle.
     * @return this BasicParticleBehavior
     */
    public BasicParticleBehavior setLifeTime(int t) {
        this.defaultLifeTime = t;
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
                go.life = go.life - dt;
            } else {
                go.life = 0;
                go.alive = false;
            }
        }
        double time = Utils.range(dt * 0.125, 0, 16);
        go.acceleration.multiply(time * 0.5);
        go.velocity.add(go.acceleration);
        go.position.add(go.velocity);

    }

    @Override
    public void onRender(Particle go, Render r) {

    }

    @Override
    public void onAction(Particle go, Integer action) {

    }
}
