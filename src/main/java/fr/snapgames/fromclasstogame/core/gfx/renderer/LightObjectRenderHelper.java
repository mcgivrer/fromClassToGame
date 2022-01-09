package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.LightObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * A {@link RenderHelper} to draw {@link LightObject}.
 *
 * @see fr.snapgames.fromclasstogame.core.entity.LightType
 * @see LightObject
 */
public class LightObjectRenderHelper extends AbstractRenderHelper implements RenderHelper<LightObject> {

    /**
     * Initialize the Light RenderHelper with the master Render.
     *
     * @param r the Render system using this RenderHelper.
     */
    public LightObjectRenderHelper(Render r) {
        super(r);
    }


    @Override
    public String getType() {
        return LightObject.class.getName();
    }

    @Override
    public void draw(Graphics2D g, LightObject l) {
        switch (l.lightType) {
            case LIGHT_SPHERE:
                l.foregroundColor = brighten(l.foregroundColor, l.intensity);
                l.colors = new Color[]{l.foregroundColor,
                        new Color(l.foregroundColor.getRed() / 2.0f, l.foregroundColor.getGreen() / 2.0f,
                                l.foregroundColor.getBlue() / 2.0f, l.foregroundColor.getAlpha() / 2.0f),
                        new Color(0.0f, 0.0f, 0.0f, 0.0f)};
                l.rgp = new RadialGradientPaint(new Point((int) (l.position.x + (10 * Math.random() * l.glitterEffect)),
                        (int) (l.position.y + (10 * Math.random() * l.glitterEffect))), (int) l.width, l.dist, l.colors);

                g.setPaint(l.rgp);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, l.intensity.floatValue()));
                g.fill(new Ellipse2D.Double(l.position.x - l.width, l.position.y - l.width, l.width * 2, l.width * 2));
                break;

            case LIGHT_CONE:
                // TODO implement the CONE light type
                break;

            case LIGHT_AMBIANT:
                Camera cam = render.getGame().getSceneManager().getCurrent().getActiveCamera();
                Configuration conf = render.getGame().getConfiguration();

                final Area ambientArea = new Area(new Rectangle2D.Double(cam.position.x, cam.position.y, conf.width, conf.height));
                g.setColor(l.foregroundColor);
                Composite c = g.getComposite();
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, l.intensity.floatValue()));
                g.fill(ambientArea);
                g.setComposite(c);
                break;
        }
    }


    /**
     * Make a color brighten.
     *
     * @param color    Color to make brighten.
     * @param fraction Darkness fraction.
     * @return Lighter color.
     */
    private Color brighten(Color color, double fraction) {

        int red = (int) Math.round(Math.min(255, color.getRed() + 255 * fraction));
        int green = (int) Math.round(Math.min(255, color.getGreen() + 255 * fraction));
        int blue = (int) Math.round(Math.min(255, color.getBlue() + 255 * fraction));

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);

    }

    @Override
    public void drawDebugInfo(Graphics2D g, LightObject go) {
        super.drawDebugInfo(g, go);
    }
}
