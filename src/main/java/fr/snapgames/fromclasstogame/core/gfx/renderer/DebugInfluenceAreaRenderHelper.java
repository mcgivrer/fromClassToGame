package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.physic.InfluenceArea2d;

import java.awt.*;

public class DebugInfluenceAreaRenderHelper extends AbstractRenderHelper implements RenderHelper<InfluenceArea2d> {


    public DebugInfluenceAreaRenderHelper(Render r) {
        super(r);
    }

    @Override
    public String getType() {
        return InfluenceArea2d.class.getName();
    }

    @Override
    public void draw(Graphics2D g, InfluenceArea2d go) {

    }

    @Override
    public void drawDebugInfo(Graphics2D g, InfluenceArea2d go) {

    }

    @Override
    public void drawDebugInfo(Graphics2D g, GameObject go) {
        super.drawDebugInfo(g, go);
    }

}
