package fr.snapgames.fromclasstogame.demo.render;

import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.demo.entity.LifeObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LifeRenderHelper implements RenderHelper {

    BufferedImage figs[];
    BufferedImage heart;
    BufferedImage cross;

    public LifeRenderHelper() {
        prepareFigures();
    }

    private void prepareFigures() {
        try {
            heart = ResourceManager.getImage("images/tiles01.png:heart");
            cross = ResourceManager.getImage("images/tiles01.png:*");
            figs = new BufferedImage[10];
            for (int i = 0; i < 10; i++) {
                figs[i] = ResourceManager.getSlicedImage("images/tiles01.png",
                        "" + i, i * 8, 3 * 16, 8, 16);
            }
        } catch (UnknownResource e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g, Object o) {
        LifeObject so = (LifeObject) o;
        g.setColor(so.color);
        drawLife(g, so.value, (int) (so.position.x), (int) (so.position.y));
    }

    /**
     * Draw score with digital characters
     *
     * @param g
     * @param value
     * @param x
     * @param y
     */
    private void drawLife(Graphics2D g, int value, int x, int y) {
        g.drawImage(heart, x, y, null);
        g.drawImage(cross, x + 8, y + 10, null);
        drawFig(g, value, x + 12, y );
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
        return LifeObject.class.getName();
    }

}
