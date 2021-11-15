package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;

import java.awt.*;

public class BasicParticleBehavior implements Behavior<Particle> {

    private ParticleSystem parent;

    public BasicParticleBehavior(ParticleSystem ps) {
        super();
        this.parent = ps;
    }

    @Override
    public void onCreate(Particle p) {
        Behavior.super.onCreate(p);
        p.alive = true;
        p.life = 1000;
        p.color = Color.WHITE;
        p.setSize(1 + (int) (Math.random() * 5));
        p.setPosition(parent.position);
        p.setAcceleration(Utils.randV2d(-1, 1, -1, 1));

    }

    @Override
    public void onInput(Particle go, ActionHandler ih) {

    }

    @Override
    public void onUpdate(Particle go, long dt) {
        if (go.alive) {
            go.life = go.life - 1;
            go.setPosition(parent.position);
        }
        if (go.life < 0) {
            go.alive = false;
            go.life = 0;
        }
    }

    @Override
    public void onRender(Particle go, Render r) {

    }

    @Override
    public void onAction(Particle go, ActionHandler.ACTIONS action) {

    }
}
