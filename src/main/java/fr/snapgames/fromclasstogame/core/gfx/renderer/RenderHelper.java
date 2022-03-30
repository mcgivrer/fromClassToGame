package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.AbstractEntity;

import java.awt.*;

public interface RenderHelper<T extends AbstractEntity> {
    String getType();

    void draw(Graphics2D g, T go);

    void drawDebugInfo(Graphics2D g, T go);

}
