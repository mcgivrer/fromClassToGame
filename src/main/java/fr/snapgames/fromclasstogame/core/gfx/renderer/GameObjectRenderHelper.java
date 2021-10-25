package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.Graphics2D;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameObjectRenderHelper implements RenderHelper {
    private static final Logger logger = LoggerFactory.getLogger(GameObjectRenderHelper.class);

    @Override
    public void draw(Graphics2D g, Object o) {
        GameObject go = (GameObject) o;
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
                if (go.image != null) {
                    g.drawImage(go.image, (int) (go.position.x), (int) (go.position.y), null);
                } else {
                    logger.error("GameObject named {} : image attribute is not defined", go.name);
                }
                break;
            default:
                logger.error("GameObject named {} : type attribute is unknown", go.name);
                break;
        }
    }

    @Override
    public String getType() {
        return GameObject.class.getName();
    }

}
