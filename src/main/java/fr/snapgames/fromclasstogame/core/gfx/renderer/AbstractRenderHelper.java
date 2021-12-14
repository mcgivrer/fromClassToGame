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
    protected Font debugFont;


    public AbstractRenderHelper(Render r) {
        this.render = r;
        debugFont = r.getBuffer().getGraphics().getFont().deriveFont(9.0f);
    }

    /**
     * Set the current active font.
     *
     * @param g    the Graphics 2D interface
     * @param font the font to be activated.
     */
    public void setFont(Graphics2D g, Font font) {
        g.setFont(font);
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
        int yOffset = g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent();
        drawText(g, text, pos.x, pos.y + yOffset);
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
        int yOffset = g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent();
        g.setColor(Color.BLACK);
        for (double x = to.position.x - maxBorderWidth; x < to.position.x + maxBorderWidth; x++) {
            for (double y = to.position.y - maxBorderWidth; y < to.position.y + maxBorderWidth; y++) {
                g.drawString(to.text, (int) (x), (int) (y) + yOffset);
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
            Vector2d pos = go.position;
            int gw = (int) go.width;
            int gh = (int) go.height;

            setColor(g, debugBoxColor);
            setFontSize(g, 8.0f);
            drawText(g, "#" + go.id, pos.x, pos.y);

            setColor(g, Color.ORANGE);
            drawRect(g, pos, gw - 1, gh - 1, 1, 1, debugBoxColor);
            if (go.getDebug() >= 1) {
                double offsetY = go.debugOffsetX;
                double offsetX = gw + go.debugOffsetY;

                setFontSize(g, 9);
                String largestString = go.getDebugInfo().stream().max((o1, o2) -> o1.length() > o2.length() ? 1 : -1).get();
                int maxWidth = g.getFontMetrics().stringWidth(largestString) < 80 ? 80 : g.getFontMetrics().stringWidth(largestString);

                int fontHeight = g.getFontMetrics().getHeight();
                int height = ((go.getDebugInfo().size()) * fontHeight);
                if (go.life != -1) {
                    drawGauge(g,
                            pos.x + offsetX - 3,
                            pos.y + offsetY - 12,
                            0, go.lifeStart,
                            go.life,
                            maxWidth + 7,
                            3);
                }
                fillRect(g, go.position, maxWidth + 8, height, offsetX - 4, offsetY - fontHeight + 2, debugBackgroundColor);
                setColor(g, debugFrontColor);
                setFont(g, debugFont);
                int i = 0;
                for (String line : go.getDebugInfo()) {
                    drawText(g, line, go.position, offsetX, offsetY + i);
                    i += fontHeight;
                }
            }
        }
    }

    /**
     * draw debug gauge on screen at (x,y)
     * spread on (width x height) rectangle,
     * from min => max value
     * using the Graphics2D g interface.
     *
     * @param g      the Graphics interface to be used.
     * @param x      the horizontal position of the gauge
     * @param y      the vertical position of the gauge
     * @param min    the minimum value of the gauge
     * @param max    the maximum value of the gauge
     * @param value  the current value of the gauge
     * @param width  width of the rectangle containing the gauge
     * @param height height of the rectangle containing the gauge
     */
    private void drawGauge(Graphics2D g, double x, double y, double min, double max, double value, double width, double height) {
        double lifeValue = width * ((min + value) / max);
        drawRect(g, new Vector2d(x - 1, y - 1), width, height, 0, 0, debugBackgroundColor);
        fillRect(g, new Vector2d(x, y), lifeValue, height - 1, 0, 0, Color.ORANGE);
    }
}
