package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;

import java.awt.*;

public class BasicParticleBehavior implements Behavior<Particle> {

    private ParticleSystem parent;
    private int defaultLifeTime = 1000;
    private Color defaultColor = Color.WHITE;

    public BasicParticleBehavior(ParticleSystem ps, int defaultLifeTime) {
        super();
        this.parent = ps;
        this.defaultLifeTime = defaultLifeTime;
    }

    @Override
    public void onCreate(Particle p) {
        Behavior.super.onCreate(p);
        p.alive = true;
        p.life = defaultLifeTime;
        p.color = Color.WHITE;
        p.setSize(2 + (int) (Math.random() * 5));
        p.setPosition(parent.position);
        p.setAcceleration(Utils.randV2d(-1, 1, -1, 1));

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
            go.life = go.life - 1;
            go.setPosition(parent.position);
        } else if (go.life < 0) {
            this.onCreate(go);
        }
    }

    @Override
    public void onRender(Particle go, Render r) {

    }

    @Override
    public void onAction(Particle go, ActionHandler.ACTIONS action) {

    }
}
