package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.TextObject;
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

    public void drawTextBorder(Graphics2D g, double maxBorderWidth, TextObject so) {
        // draw black border
        g.setColor(Color.BLACK);
        for (double x = so.position.x - maxBorderWidth; x < so.position.x + maxBorderWidth; x++) {
            for (double y = so.position.y - maxBorderWidth; y < so.position.y + maxBorderWidth; y++) {
                g.drawString(so.text, (int) (x), (int) (y));
            }
        }
    }

    public void drawImage(Graphics2D g, BufferedImage img, double x, double y) {
        g.drawImage(img, (int) x, (int) y, null);
    }

    public void drawImage(Graphics2D g, BufferedImage img, Vector2d pos) {
        drawImage(g, img, pos.x, pos.y);
    }

    public void drawImage(Graphics2D g, BufferedImage img, Vector2d pos, double width, double height) {
        g.drawImage(img, (int) pos.x, (int) pos.y, (int) width, (int) height, null);
    }

    public void drawPoint(Graphics2D g, Vector2d position, double size, Color color) {
        g.setColor(color);
        g.fillOval((int) position.x, (int) position.y, (int) size, (int) size);
    }

}
