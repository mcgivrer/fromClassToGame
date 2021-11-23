package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class BasicParticleBehavior implements Behavior<Particle> {
    private static final Logger logger = LoggerFactory.getLogger(BasicParticleBehavior.class);

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
        double time = Utils.range(dt * 0.25, 1, 4);
        go.acceleration.multiply(time * 0.5);
        go.velocity.add(go.acceleration).multiply(time);
        go.position.add(go.velocity.multiply(time));

        //logger.info("p{}({}):p={},v={},a={}", go.id, time, go.position, go.velocity, go.acceleration);
    }

    @Override
    public void onRender(Particle go, Render r) {

    }

    @Override
    public void onAction(Particle go, ActionHandler.ACTIONS action) {

    }
}
