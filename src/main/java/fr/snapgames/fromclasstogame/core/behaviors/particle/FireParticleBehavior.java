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
    public void onCreate(Particle entity) {
        super.onCreate(entity);
        entity.life = 300;
        entity.alive = true;
        entity.color = colorStart;
        entity.setSize((int)(2.0 + (Math.random() * 6.0)));
        entity.setPosition(Utils.add(parent.position, Utils.randV2d(-8, 0, -2, 2)));
        entity.setVelocity(new Vector2d(0.0, 0.0));
        entity.acceleration = Utils.randV2d(-0.0008, 0.0008, -0.15, -0.35);
    }

    @Override
    public void onUpdate(Particle entity, long elapsed) {
        super.onUpdate(entity, elapsed);
        entity.life -= elapsed;
        /*p.color = fade(p.color, dt);*/
        if (entity.width > 0) {
            entity.width *= 0.98;
        } else {
            entity.life = 0;
            entity.width = 1;
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
