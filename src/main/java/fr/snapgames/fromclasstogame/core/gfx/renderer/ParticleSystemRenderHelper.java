package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;

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
        if (g != null) {
            go.particles.forEach(p -> {
                drawPoint(g, p.position, p.size, color);
            });
        }
    }
}
