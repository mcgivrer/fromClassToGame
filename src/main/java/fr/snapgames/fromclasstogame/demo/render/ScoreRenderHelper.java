package fr.snapgames.fromclasstogame.demo.render;

import java.awt.Graphics2D;

import fr.snapgames.fromclasstogame.core.gfx.RenderHelper;
import fr.snapgames.fromclasstogame.demo.entity.ScoreObject;

public class ScoreRenderHelper implements RenderHelper {

    @Override
    public void draw(Graphics2D g, Object o) {
        ScoreObject so = (ScoreObject) o;
        g.setFont(so.font);
        g.drawString(so.text, (int) (so.x), (int) (so.y));

    }

    @Override
    public String getType() {
        return ScoreObject.class.getName();
    }

}
