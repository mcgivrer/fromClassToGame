package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.*;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameObjectRenderHelper extends AbstractRenderHelper implements RenderHelper {
    private static final Logger logger = LoggerFactory.getLogger(GameObjectRenderHelper.class);

    private Color debugBackgroundColor = new Color(0.1f, 0.1f, 0.1f, 0.7f);
    private Color debugFrontColor = Color.ORANGE;

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
                    drawImage(g, go.image, go.position);
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
            drawText(g, "pos:" + go.position.toString(), go.position, offsetX, offsetY);
            drawText(g, "vel:" + go.velocity.toString(), go.position, offsetX, offsetY + 10);
            drawText(g, "acc:" + go.acceleration.toString(), go.position, offsetX, offsetY + 20);
            drawText(g, "friction:" + go.material.dynFriction, go.position, offsetX, offsetY + 30);
            drawText(g, "contact:" + go.getAttribute("touching"), go.position, offsetX, offsetY + 40);
            drawText(g, "jumping:" + go.getAttribute("jumping"), go.position, offsetX, offsetY + 50);
        }
    }

    @Override
    public String getType() {
        return GameObject.class.getName();
    }

}
