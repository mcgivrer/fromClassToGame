package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.tilemap.TileMap;

import java.awt.*;

public class TileMapRenderHelper implements RenderHelper {
    @Override
    public String getType() {
        return TileMapRenderHelper.class.getName();
    }

    @Override
    public void draw(Graphics2D g, Object go) {
        TileMap tm = (TileMap) go;
    }
}
