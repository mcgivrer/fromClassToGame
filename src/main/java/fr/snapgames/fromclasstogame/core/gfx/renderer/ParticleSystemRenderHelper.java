package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;

import java.awt.*;

public class ParticleSystemRenderHelper extends AbstractRenderHelper implements RenderHelper<ParticleSystem> {

    public ParticleSystemRenderHelper(Renderer r) {
        super(r);
    }

    @Override
    public String getType() {
        return ParticleSystem.class.getName();
    }

    @Override
    public void draw(Graphics2D g, ParticleSystem go) {
        if (g != null) {
            go.getChild().forEach(p -> drawPoint(g, p.position, (p.width + p.height) / 2, p.color));
        }
    }

    @Override
    public void drawDebugInfo(Graphics2D g, ParticleSystem go) {
        super.drawDebugInfo(g, go);
    }
}
