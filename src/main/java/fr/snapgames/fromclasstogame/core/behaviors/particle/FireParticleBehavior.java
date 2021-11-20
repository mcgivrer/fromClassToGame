package fr.snapgames.fromclasstogame.core.behaviors.particle;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.Particle;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;

public class FireParticleBehavior implements Behavior<Particle> {

    @Override
    public void onCreate(Particle go) {
        go.velocity = Utils.randV2d(-4, 4, 10, 30);
        go.alive = true;
    }

    @Override
    public void onInput(Particle go, ActionHandler ih) {

    }

    @Override
    public void onUpdate(Particle go, long dt) {
        go.life += Utils.rand(0, 4);

    }

    @Override
    public void onRender(Particle go, Render r) {

    }

    @Override
    public void onAction(Particle go, ActionHandler.ACTIONS action) {

    }
}
