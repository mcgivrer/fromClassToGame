package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.physic.Utils;

import java.awt.*;

public class ParticleSystemRenderHelper extends AbstractRenderHelper implements RenderHelper<ParticleSystem> {
    private Color color;

    public ParticleSystemRenderHelper() {
        this.color = Color.RED;
    }

    @Override
    public String getType() {
        return ParticleSystem.class.getName();
    }

    @Override
    public void draw(Graphics2D g, ParticleSystem go) {
        go.particles.forEach(p -> {
            drawPoint(g, Utils.add(go.position, p.position), p.size, color);
        });
    }
}
