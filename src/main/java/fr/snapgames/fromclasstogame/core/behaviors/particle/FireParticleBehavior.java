package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.awt.*;

public class FireParticleBehavior extends BasicParticleBehavior implements Behavior<Particle> {

    public FireParticleBehavior(ParticleSystem ps, int defaultLifeTimeMS, boolean restart) {
        super(ps,defaultLifeTimeMS,restart);
    }

    @Override
    public void onCreate(Particle p) {
        super.onCreate(p);
        p.life = (long) Utils.rand(500, 800);
        p.alive = true;
        p.color = Utils.randomColor(Color.RED, Color.YELLOW);
        p.setSize(1 + (int) (Math.random() * 4));
        p.setPosition(Utils.add(parent.position, Utils.randV2d(-2, 2, -2, 2)));
        p.setVelocity(new Vector2d(0.0, 0.0));
        p.acceleration = Utils.randV2d(-0.00005, 0.00005, -0.03, -0.07);
    }

    @Override
    public void onInput(Particle go, ActionHandler ih) {

    }

    @Override
    public void onUpdate(Particle p, long dt) {
        super.onUpdate(p,dt);
        p.life -= dt;
        p.color = fade(p.color, dt);
        if (p.size > 0) {
            p.size *= 0.998;
        } else {
            p.life = 0;
            p.size = 1;
        }
    }

    private Color fade(Color c0, float dt) {
        Color c1 = new Color(
                rollColor(c0.getRed(), dt),
                rollColor(c0.getGreen(), dt),
                rollColor(c0.getBlue(), dt),
                rollColor(c0.getAlpha(), dt));
        return c1;
    }

    private float rollColor(int c, float dt) {
        return (float) Utils.range(c - (dt / 255.0), 0.0, 1.0);
    }

    @Override
    public void onRender(Particle go, Render r) {

    }

    @Override
    public void onAction(Particle go, ActionHandler.ACTIONS action) {

    }
}
