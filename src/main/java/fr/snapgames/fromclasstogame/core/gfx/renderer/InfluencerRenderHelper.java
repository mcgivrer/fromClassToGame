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
    public void drawDebugInfo(Graphics2D g, Influencer i) {
        if (renderer.getDebugLevel() >= i.debugLevel) {
            switch (i.box.type) {
                case RECTANGLE:
                    renderer.drawRectangle(g, i.debugLineColor, i.debugFillColor, i.box.shape);
                    break;
                case CIRCLE:
                    renderer.drawEllipse(g, i.debugLineColor, i.debugFillColor, i.box.ellipse);
                    break;
                default:
                    break;
            }
            renderer.drawTextWithBackground(g, i.name,
                    i.debugLineColor, i.debugFillColor,
                    i.position.x + i.debugOffsetX, i.position.y + i.debugOffsetY);
        }
    }

    @Override
    public void drawDebugInfo(Graphics2D g, GameObject go) {
        super.drawDebugInfo(g, go);
    }

}
