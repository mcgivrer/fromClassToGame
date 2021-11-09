package fr.snapgames.fromclasstogame.demo.render;

import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.demo.entity.ScoreObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;

public class ScoreRenderHelper implements RenderHelper<ScoreObject> {

    BufferedImage figuresImg;
    BufferedImage figs[];

    public ScoreRenderHelper() {
        prepareFigures();
    }

    private void prepareFigures() {
        figs = new BufferedImage[10];
        for (int i = 0; i < 10; i++) {
            figs[i] = ResourceManager.getSlicedImage("images/tiles01.png",
                    "" + i, i * 8, 3 * 16, 8, 16);
        }
    }

    @Override
    public void draw(Graphics2D g, ScoreObject so) {
        double maxBorderWidth = 2;
        g.setColor(so.color);
        drawScore(g, so.text, (int) (so.position.x), (int) (so.position.y));
    }


    /**
     * Draw score with digital characters
     *
     * @param g
     * @param score
     * @param x
     * @param y
     */
    private void drawScore(Graphics2D g, String score, int x, int y) {
        byte c[] = score.getBytes(StandardCharsets.US_ASCII);
        for (int pos = 0; pos < score.length(); pos++) {
            int v = c[pos];
            drawFig(g, v - 48, x + (pos * 8), y);
        }
    }

    /**
     * Draw a simple figure
     *
     * @param g
     * @param value
     * @param x
     * @param y
     */
    private void drawFig(Graphics2D g, int value, int x, int y) {
        assert (value > -1);
        assert (value < 10);
        g.drawImage(figs[value], x, y, null);
    }

    @Override
    public String getType() {
        return ScoreObject.class.getName();
    }

}
