package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class GameObjectRenderHelper extends AbstractRenderHelper implements RenderHelper<GameObject> {
    private static final Logger logger = LoggerFactory.getLogger(GameObjectRenderHelper.class);


    @Override
    public void draw(Graphics2D g, GameObject go) {
        if(go!=null) {
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
                        drawImage(g, go.image, go.position, go.width, go.height);
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
