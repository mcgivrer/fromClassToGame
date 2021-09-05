package fr.snapgames.fromclasstogame.demo.render;

import java.awt.*;

import fr.snapgames.fromclasstogame.core.gfx.RenderHelper;
import fr.snapgames.fromclasstogame.demo.scenes.ScoreObject;

public class ScoreRenderHelper implements RenderHelper {

    @Override
    public void draw(Graphics2D g, Object o) {
        double maxBorderWidth = 2;
        ScoreObject so = (ScoreObject) o;
        g.setColor(so.color);
        g.setFont(so.font);
        drawBorder(g, maxBorderWidth, so);
        g.setColor(so.color);
        g.drawString(so.text, (int) (so.x), (int) (so.y));
    }

    private void drawBorder(Graphics2D g, double maxBorderWidth, ScoreObject so) {
        // draw black border
        g.setColor(Color.BLACK);
        for (double x = so.x - maxBorderWidth; x < so.x + maxBorderWidth; x++) {
            for (double y = so.y - maxBorderWidth; y < so.y + maxBorderWidth; y++) {
                g.drawString(so.text, (int) (x), (int) (y));
            }
        }
    }

    @Override
    public String getType() {
        return ScoreObject.class.getName();
    }

}
