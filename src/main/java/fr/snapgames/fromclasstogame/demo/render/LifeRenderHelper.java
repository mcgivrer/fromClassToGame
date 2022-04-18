package fr.snapgames.fromclasstogame.demo.render;

import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.gfx.renderer.AbstractRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.demo.entity.LifeObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LifeRenderHelper extends AbstractRenderHelper implements RenderHelper<LifeObject> {

    BufferedImage[] figs;
    BufferedImage heart;
    BufferedImage cross;

    public LifeRenderHelper(Renderer r) {
        super(r);
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
    public void draw(Graphics2D g, LifeObject lo) {
        g.setColor(lo.color);
        drawLife(g, lo.value, (int) (lo.position.x), (int) (lo.position.y));
    }

    @Override
    public void drawDebugInfo(Graphics2D g, LifeObject go) {
        super.drawDebugInfo(g, go);
    }

    /**
     * Draw score with digital characters
     *
     * @param g     the Graphics2D API
     * @param value life value to draw
     * @param x     horizontal position
     * @param y     vertical position
     */
    private void drawLife(Graphics2D g, int value, int x, int y) {
        g.drawImage(heart, x, y, null);
        g.drawImage(cross, x + 8, y + 10, null);
        drawFig(g, value, x + 12, y);
    }

    /**
     * Draw a simple figure
     *
     * @param g     the Graphics2D API
     * @param value number to draw
     * @param x     horizontal position
     * @param y     vertical position
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
