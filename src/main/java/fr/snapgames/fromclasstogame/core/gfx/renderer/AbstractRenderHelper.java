package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.gfx.Window;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Internal Helpers to draw to the internal image buffer of the Render system.
 *
 * @author Frédéric Delorme
 * @since 1.0.0
 */
public class AbstractRenderHelper {
    private final Render render;
    protected Color debugBackgroundColor = new Color(0.1f, 0.1f, 0.1f, 0.7f);
    protected Color debugFrontColor = Color.ORANGE;
    protected Color debugBoxColor = Color.YELLOW;


    public AbstractRenderHelper(Render r) {
        this.render = r;
    }

    /**
     * Simple set active Font
     *
     * @param g    the Graphics 2D interface
     * @param size size of the font to be derived to.
     */
    public void setFontSize(Graphics2D g, float size) {
        g.setFont(g.getFont().deriveFont(size));
    }

    /**
     * Define the {@link Color} to be used on the next graphics pipeline operations
     *
     * @param g the Graphics 2D interface
     * @param c the {@link Color} object
     */
    public void setColor(Graphics2D g, Color c) {
        g.setColor(c);
    }

    /**
     * Drawing a text with the active font
     *
     * @param g    the Graphics 2D interface
     * @param text the text to be drawn
     * @param x    the horizontal position where to draw
     * @param y    the horizontal position where to draw
     */
    public void drawText(Graphics2D g, String text, double x, double y) {
        g.drawString(text, (int) x, (int) y);
    }


    /**
     * Drawing a text with the active font
     *
     * @param g    the Graphics 2D interface
     * @param text the text to be drawn
     * @param pos  the position where to draw
     */
    public void drawText(Graphics2D g, String text, Vector2d pos) {
        drawText(g, text, pos.x, pos.y);
    }

    /**
     * Drawing some text with black border from a TextObject.
     *
     * @param g              the Graphics 2D interface
     * @param maxBorderWidth the size of the border to be drawn
     * @param to             the Text Object
     */
    public void drawTextBorder(Graphics2D g, double maxBorderWidth, TextObject to) {
        // draw black border
        g.setColor(Color.BLACK);
        for (double x = to.position.x - maxBorderWidth; x < to.position.x + maxBorderWidth; x++) {
            for (double y = to.position.y - maxBorderWidth; y < to.position.y + maxBorderWidth; y++) {
                g.drawString(to.text, (int) (x), (int) (y));
            }
        }
    }


    /**
     * Drawing a text with the active font
     *
     * @param g    the Graphics 2D interface
     * @param text the text to be drawn
     * @param pos  the position Vector2d where to draw
     * @param offX an horizontal offset where to move the text to draw
     * @param offY a vertical offset where to move the text to draw
     */
    public void drawText(Graphics2D g, String text, Vector2d pos, double offX, double offY) {
        drawText(g, text, pos.x + offX, pos.y + offY);
    }

    /**
     * Drawing a filled rectangle at the <code>pos</code> position having a <code>width</code> and a <code>height</code>
     * And a possible offset at <code>offX</code>,<code>offY</code>, using the <code>c</code> {@link Color}.
     *
     * @param g      the Graphics 2D interface
     * @param pos    the position Vector2d where to draw
     * @param width  the width of the filled rectangle
     * @param height the height of the filled rectangle
     * @param offX   the horizontal offset
     * @param offY   the vertical offset
     * @param c      the {@link Color} to use
     */
    public void fillRect(Graphics2D g, Vector2d pos, double width, double height, double offX, double offY, Color c) {
        g.setColor(c);
        g.fillRect((int) (pos.x + offX), (int) (pos.y + offY), (int) width, (int) height);
    }

    /**
     * Drawing a rectangle at the <code>pos</code> position having a <code>width</code> and a <code>height</code>
     * And a possible offset at <code>offX</code>,<code>offY</code>, using the <code>c</code> {@link Color}.
     *
     * @param g       the Graphics 2D interface
     * @param pos     the position Vector2d where to draw
     * @param width   the width of the rectangle
     * @param height  the height of the rectangle
     * @param marginX the horizontal margin
     * @param marginY the vertical margin
     * @param c       the {@link Color} to use
     */
    public void drawRect(Graphics2D g, Vector2d pos, double width, double height, double marginX, double marginY, Color c) {
        g.setColor(c);
        g.drawRect((int) (
                        pos.x - marginX), (int) (pos.y - marginY),
                (int) (width + (2 * marginX)), (int) (height + (2 * marginY)));
    }


    /**
     * Draw an image <code>img</code> (see {@link BufferedImage}) at <code>x,y</code>.
     *
     * @param g   the Graphics 2D interface
     * @param img the image to be drawn
     * @param x   the horizontal position where to draw
     * @param y   the horizontal position where to draw
     */
    public void drawImage(Graphics2D g, BufferedImage img, double x, double y) {
        g.drawImage(img, (int) x, (int) y, null);
    }

    /**
     * Draw an image <code>img</code> (see {@link BufferedImage}) at <code>pos</code> {@link Vector2d}.
     *
     * @param g   the Graphics 2D interface
     * @param img the image to be drawn
     * @param pos the  position where to draw
     */
    public void drawImage(Graphics2D g, BufferedImage img, Vector2d pos) {
        drawImage(g, img, pos.x, pos.y);
    }


    /**
     * Draw an image <code>img</code> (see {@link BufferedImage}) at <code>pos</code> {@link Vector2d}.
     *
     * @param g      the Graphics 2D interface
     * @param img    the image to be drawn
     * @param pos    the  position where to draw
     * @param width  the width of the image to be drawn to
     * @param height the height of the image to be drawn to
     */
    public void drawImage(Graphics2D g, BufferedImage img, Vector2d pos, double width, double height) {
        g.drawImage(img, (int) pos.x, (int) pos.y, (int) width, (int) height, null);
    }

    /**
     * Draw a 2D point at position {@link Vector2d} with a defined size using the right {@link Color}.
     *
     * @param g        the Graphics 2D interface
     * @param position the  position where to draw
     * @param size     the size of the point to draw
     * @param color    the color to use to draw the point.
     */
    public void drawPoint(Graphics2D g, Vector2d position, double size, Color color) {
        g.setColor(color);
        g.fillOval(
                (int) position.x,
                (int) position.y,
                (int) size,
                (int) size);
    }

    /**
     * If {@link GameObject#getDebug()} >= {@link Window#getDebug()}, and  {@link Window#getDebug()}>0, display the debug information.
     *
     * @param g  the Graphics API to use to draw information
     * @param go the GameObject to be processed
     */
    public void drawDebugInfo(Graphics2D g, GameObject go) {
        int winDbgLevel = render.getGame().getWindow().getDebug();
        if (winDbgLevel > 0 && winDbgLevel >= go.getDebug()) {
            setColor(g, debugBoxColor);
            setFontSize(g, 8.0f);
            drawText(g, "#" + go.id, go.position.x, go.position.y);
            drawRect(g, go.position, go.width - 1, go.height - 1, 1, 1, debugBoxColor);
            if (go.life > -1) {
                drawGauge(g, go, go.position.x, go.position.y, 0, go.lifeStart, go.life, 40, 2);
            }
            if (go.getDebug() >= 2) {
                setFontSize(g, 9);
                double offsetY = go.debugOffsetX;
                double offsetX = go.width + go.debugOffsetY;

                String largestString = go.getDebugInfo().stream().max((o1, o2) -> o1.length() > o2.length() ? 1 : -1).get();
                int maxWidth = g.getFontMetrics().stringWidth(largestString);

                int height = ((go.getDebugInfo().size() + 2) * 9);
                fillRect(g, go.position, maxWidth + 8, height, offsetX - 4, offsetY - 12, debugBackgroundColor);
                setColor(g, debugFrontColor);
                int i = 0;
                for (String line : go.getDebugInfo()) {
                    drawText(g, line, go.position, offsetX, offsetY + i);
                    i += 10;
                }
            }
        }
    }

    private void drawGauge(Graphics2D g, GameObject go, double x, double y, double min, double max, double value, double width, double height) {
        double lifeValue = width * ((min + value) / max);
        drawRect(g, new Vector2d(x - 21, y - 21), width, height, 0, 0, Color.DARK_GRAY);
        fillRect(g, new Vector2d(x - 20, y - 20), lifeValue, 1, 0, 0, Color.BLUE);
    }
}
