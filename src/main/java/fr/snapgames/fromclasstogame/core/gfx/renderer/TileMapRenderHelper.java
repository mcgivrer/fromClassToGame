package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.tilemap.TileMap;
import fr.snapgames.fromclasstogame.core.gfx.Render;

import java.awt.*;

public class TileMapRenderHelper extends AbstractRenderHelper implements RenderHelper<TileMap> {
    public TileMapRenderHelper(Render r) {
        super(r);
    }

    @Override
    public String getType() {
        return TileMapRenderHelper.class.getName();
    }

    @Override
    public void draw(Graphics2D g, TileMap go) {
        TileMap tm = (TileMap) go;
    }

    @Override
    public void drawDebugInfo(Graphics2D g, TileMap go) {
        super.drawDebugInfo(g, go);
    }
}
