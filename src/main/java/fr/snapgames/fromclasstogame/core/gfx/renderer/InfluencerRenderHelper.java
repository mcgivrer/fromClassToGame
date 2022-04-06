package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.physic.Influencer;

import java.awt.*;

public class InfluencerRenderHelper extends AbstractRenderHelper implements RenderHelper<Influencer> {


    public InfluencerRenderHelper(Renderer r) {
        super(r);
    }

    @Override
    public String getType() {
        return Influencer.class.getName();
    }

    @Override
    public void draw(Graphics2D g, Influencer go) {

    }

    @Override
    public void drawDebugInfo(Graphics2D g, Influencer go) {
        renderer.drawRectangle(g, Color.RED, new Color(0.8f, 0.4f, 0.2f, 0.8f), go.bbox.shape);
    }

    @Override
    public void drawDebugInfo(Graphics2D g, GameObject go) {
        super.drawDebugInfo(g, go);
    }

}
