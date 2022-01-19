package fr.snapgames.fromclasstogame.core.behaviors.particle;

import java.awt.Color;

import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.physic.Utils;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class FireParticleBehavior extends BasicParticleBehavior{

    private Color colorStart = new Color(0.9f,0.4f,0.2f,0.5f);
    private Color colorEnd = Color.RED;

    private int colorTransitionDuration = 0;


    public FireParticleBehavior(ParticleSystem ps, int defaultLifeTimeMS, boolean restart) {
        super(ps, defaultLifeTimeMS, restart);
        colorTransitionDuration = 0;
    }

    @Override
    public void onCreate(Particle p) {
        super.onCreate(p);
        p.life = 300;
        p.alive = true;
        p.color = colorStart;
        p.setSize((int)(2.0 + (Math.random() * 6.0)));
        p.setPosition(Utils.add(parent.position, Utils.randV2d(-8, 0, -2, 2)));
        p.setVelocity(new Vector2d(0.0, 0.0));
        p.acceleration = Utils.randV2d(-0.0008, 0.0008, -0.15, -0.35);
    }

    @Override
    public void onUpdate(Particle p, long dt) {
        super.onUpdate(p, dt);
        p.life -= dt;
        /*p.color = fade(p.color, dt);*/
        if (p.width > 0) {
            p.width *= 0.98;
        } else {
            p.life = 0;
            p.width = 1;
        }
    }

    private Color fade(Color c0, float dt) {
        return new Color(
                rollColor(c0.getRed(), dt),
                rollColor(c0.getGreen(), dt),
                rollColor(c0.getBlue(), dt),
                0.9f);
    }

    private float rollColor(int c, float dt) {
        return (float) Utils.range(c - (dt / 255.0), 0.0, 1.0);
    }
}
