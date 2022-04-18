package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObjectRenderHelper extends AbstractRenderHelper implements RenderHelper<GameObject> {
    private static final Logger logger = LoggerFactory.getLogger(GameObjectRenderHelper.class);

    public GameObjectRenderHelper(Renderer r) {
        super(r);
    }

    @Override
    public void draw(Graphics2D g, GameObject go) {
        if (go != null) {
            switch (go.type) {
                case POINT:
                    g.setColor(go.color);
                    g.drawLine((int) (go.position.x), (int) (go.position.y), (int) (go.position.x), (int) (go.position.y));
                    break;
                case RECTANGLE:
                    g.setColor(go.color);
                    g.drawRect((int) (go.position.x), (int) (go.position.y), (int) (go.width), (int) (go.height));
                    break;
                case CIRCLE:
                    g.setColor(go.color);
                    g.drawArc((int) (go.position.x), (int) (go.position.y), (int) (go.width), (int) (go.height), 0, 360);
                    break;
                case IMAGE:
                    // retrieve the active image through Animation class or as internal fixed image.
                    BufferedImage img = go.getAnimations() != null ? go.getAnimations().getCurrentFrame() : go.image;
                    if (img != null) {
                        drawImage(g, img, go.position, go.width, go.height);
                    } else {
                        logger.error("GameObject named {} : image attribute is not defined", go.name);
                    }
                    break;
                default:
                    logger.error("GameObject named {} : type attribute is unknown", go.name);
                    break;
            }
            drawDebugInfo(g, go);
        }
    }

    @Override
    public String getType() {
        return GameObject.class.getName();
    }

}
