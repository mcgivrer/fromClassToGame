package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;

import java.awt.*;

public class BasicParticleBehavior implements Behavior<Particle> {

    private ParticleSystem parent;
    private long defaultLifeTime = 1000;
    private Color defaultColor = Color.WHITE;

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
        p.color = Color.WHITE;
        p.setSize(1 + (int) (Math.random() * 2));
        p.setPosition(parent.position);
    }

    /**
     * Define the default Color for the new particle
     *
     * @param c
     * @return
     */
    public BasicParticleBehavior setColor(Color c) {
        this.defaultColor = c;
        return this;
    }

    /**
     * Set the default lifetime t for the new particle.
     *
     * @param t
     * @return
     */
    public BasicParticleBehavior setLifeTime(int t) {
        this.defaultLifeTime = t;
        return this;
    }

    @Override
    public void onInput(Particle go, ActionHandler ih) {

    }

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
        double time = (double)dt / 1000.0;
        //go.velocity.add(go.acceleration).multiply(time);
        go.position.add(go.velocity.multiply(time));
    }

    @Override
    public void onRender(Particle go, Render r) {

    }

    @Override
    public void onAction(Particle go, ActionHandler.ACTIONS action) {

    }
}
