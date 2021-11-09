package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.Graphics2D;

public interface RenderHelper<T> {
    String getType();

    void draw(Graphics2D g, T go);

}
