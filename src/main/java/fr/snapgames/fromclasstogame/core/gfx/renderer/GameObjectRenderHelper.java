package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.*;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameObjectRenderHelper extends AbstractRenderHelper implements RenderHelper<GameObject> {
    private static final Logger logger = LoggerFactory.getLogger(GameObjectRenderHelper.class);

    private Color debugBackgroundColor = new Color(0.1f, 0.1f, 0.1f, 0.7f);
    private Color debugFrontColor = Color.ORANGE;

    @Override
    public void draw(Graphics2D g, GameObject go) {
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
        if (go.getDebug() > 0) {
            setFontSize(g, 9);
            double offsetY = -40;
            double offsetX = go.width + 8;
            fillRect(g, go.position, 100, 70, go.width + 1, offsetY - 12, debugBackgroundColor);
            setColor(g, debugFrontColor);
            int i = 0;
            for (String line : go.getDebugInfo()) {
                drawText(g, line, go.position, offsetX, offsetY + i);
                i += 10;
            }
        }
    }

    @Override
    public String getType() {
        return GameObject.class.getName();
    }

}
