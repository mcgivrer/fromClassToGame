package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.snapgames.fromclasstogame.core.entity.DebugViewportGrid;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.physic.World;

public class DebugViewportGridRenderHelper extends AbstractRenderHelper implements RenderHelper<DebugViewportGrid> {

    private Color gridColor = new Color(0.1f,0.4f,0.8f,0.4f);


    public DebugViewportGridRenderHelper(Renderer r) {
        super(r);
    }

    @Override
    public String getType() {
        return DebugViewportGrid.class.getName();
    }

    @Override
    public void draw(Graphics2D g, DebugViewportGrid dvg) {
        if (dvg.getWorld() != null && dvg.getDebug() > 0) {
            World w = dvg.getWorld();
            g.setColor(gridColor);
            for (int x = 0; x < w.width; x += dvg.gridX) {
                g.drawRect(x, 0, dvg.gridX, (int) w.height);
            }
            for (int y = 0; y < w.height; y += dvg.gridY) {
                if (y + dvg.gridY < w.height) {
                    g.drawRect(0, y, (int) w.width, dvg.gridY);
                }
            }
            g.setColor(debugBoxColor);
            g.drawRect(0, 0, (int) w.width, (int) w.height);
        }
    }

    @Override
    public void drawDebugInfo(Graphics2D g, DebugViewportGrid go) {
        super.drawDebugInfo(g,go);
    }
}
