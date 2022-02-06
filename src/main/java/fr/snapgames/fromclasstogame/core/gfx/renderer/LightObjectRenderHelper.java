package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.LightObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;

/**
 * A {@link RenderHelper} to draw {@link LightObject}.
 *
 * @author Frédéric Delorme
 * @see fr.snapgames.fromclasstogame.core.entity.LightType
 * @see LightObject
 * @since 1.0.2
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
                drawSphericalLight(g, l);
                break;
            case LIGHT_SPOT:
                drawSpotLight(g, l);
                break;
            case LIGHT_AMBIANT:
                drawAmbientLight(g, l);
                break;
            default:
                break;
        }
        l.rendered = true;
    }

    private void drawSpotLight(Graphics2D g, LightObject l) {

        assert (l.target != null);
        double rotation = Math.acos(((l.position.x * l.target.x) + (l.position.y * l.target.y)) /
                (Math.sqrt((l.position.x * l.position.x) + (l.position.y * l.position.y))
                        * Math.sqrt((l.target.x * l.target.x) + (l.target.y * l.target.y))));
        g.rotate(rotation);
        //TODO draw filled triangle.
    }

    private void drawAmbientLight(Graphics2D g, LightObject l) {
        Camera cam = render.getGame().getSceneManager().getCurrent().getActiveCamera();
        Configuration conf = render.getGame().getConfiguration();

        final Area ambientArea = new Area(new Rectangle2D.Double(cam.position.x, cam.position.y, conf.width, conf.height));
        g.setColor(l.foregroundColor);
        Composite c = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, l.intensity.floatValue()));
        g.fill(ambientArea);
        g.setComposite(c);
    }

    private void drawSphericalLight(Graphics2D g, LightObject l) {
        l.foregroundColor = brighten(l.foregroundColor, l.intensity);
        Color medColor = brighten(l.foregroundColor, l.intensity / 2.0f);
        Color endColor = new Color(0.0f, 0.0f, 0.0f, 0.2f);

        l.colors = new Color[]{l.foregroundColor,
                medColor,
                endColor};
        l.dist = new float[]{0.0f, 0.1f, 1.0f};
        l.rgp = new RadialGradientPaint(new Point((int) (l.position.x + (10 * Math.random() * l.glitterEffect)),
                (int) (l.position.y + (10 * Math.random() * l.glitterEffect))), (int) l.width, l.dist, l.colors);
        g.setPaint(l.rgp);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, l.intensity.floatValue()));
        g.fill(new Ellipse2D.Double(l.position.x - l.width, l.position.y - l.width, l.width * 2, l.width * 2));
    }


    /**
     * Make a color brighten.
     *
     * @param color    Color to make brighten.
     * @param fraction Darkness fraction.
     * @return Lighter color.
     * @link https://stackoverflow.com/questions/18648142/creating-brighter-color-java
     */
    public static Color brighten(Color color, double fraction) {

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
