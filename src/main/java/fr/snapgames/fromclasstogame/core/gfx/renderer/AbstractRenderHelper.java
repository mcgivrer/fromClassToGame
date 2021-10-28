package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AbstractRenderHelper {

    public void setFontSize(Graphics2D g, float size) {
        g.setFont(g.getFont().deriveFont(size));
    }

    public void setColor(Graphics2D g, Color c) {
        g.setColor(c);
    }

    public void drawText(Graphics2D g, String text, double x, double y) {
        g.drawString(text, (int) x, (int) y);
    }

    public void drawText(Graphics2D g, String text, Vector2d pos, double offX, double offY) {
        drawText(g, text, pos.x + offX, pos.y + offY);
    }

    public void fillRect(Graphics2D g, Vector2d pos, double width, double height, double offX, double offY, Color c) {
        g.setColor(c);
        g.fillRect((int) (pos.x + offX), (int) (pos.y + offY), (int) width, (int) height);
    }

    public void drawText(Graphics2D g, String text, Vector2d pos) {
        drawText(g, text, pos.x, pos.y);
    }

    public void drawImage(Graphics2D g, BufferedImage img, double x, double y) {
        g.drawImage(img, (int) x, (int) y, null);
    }

    public void drawImage(Graphics2D g, BufferedImage img, Vector2d pos) {
        drawImage(g, img, pos.x, pos.y);
    }
}
