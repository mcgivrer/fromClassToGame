package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class BasicParticleBehavior implements Behavior<Particle> {
    private static final Logger logger = LoggerFactory.getLogger(BasicParticleBehavior.class);

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
        double time = Utils.range(dt * 0.125, 0, 16);
        go.acceleration.multiply(time * 0.5);
        go.velocity.add(go.acceleration);
        go.position.add(go.velocity);

        //logger.info("p{}({}):p={},v={},a={}", go.id, time, go.position, go.velocity, go.acceleration);
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
