package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.*;

public interface RenderHelper<T> {
    String getType();

    void draw(Graphics2D g, T go);

    void drawDebugInfo(Graphics2D g, T go);

}
