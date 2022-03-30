package fr.snapgames.fromclasstogame.core.gfx;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.AbstractEntity;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.renderer.GameObjectRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.TextRenderHelper;
import fr.snapgames.fromclasstogame.core.physic.Influencer;
import fr.snapgames.fromclasstogame.core.physic.World;
import fr.snapgames.fromclasstogame.core.physic.collision.Box;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * The Render system ti draw every thing the GameObject needs to.
 *
 * @author Frédéric Delorme
 * @since 0.0.1
 */
public class Renderer extends System {

    private static final Logger logger = LoggerFactory.getLogger(Renderer.class);
    /**
     * Internal index of screenshots.
     */
    private static int screenShotIndex = 0;
    /**
     * THe internal render buffer.
     */
    private BufferedImage buffer;
    /**
     * The viewport dimension
     */
    private Dimension viewport;

    /**
     * the current active camera.
     */
    private Camera camera;
    /**
     * The list of available camera.
     */
    private final List<GameObject> objectsRelativeToCamera = new CopyOnWriteArrayList<>();
    /**
     * Internal ist of Render helpers
     */
    private final Map<String, RenderHelper<?>> renderHelpers = new HashMap<>();

    /**
     * debug color to display debug information
     */
    private final Color debugColor = Color.ORANGE;
    /**
     * Debug level.
     */
    private int debug = 0;

    /**
     * the world object to be used by the Render
     */
    private World world;
    /**
     * Flag to gather a screenshot of the rendering buffer.
     */
    private boolean renderScreenshot = false;

    /**
     * Create a new Render system, linked to the parent {@link Game}.
     *
     * @param g the parent Game
     */
    public Renderer(Game g) {
        super(g);
    }

    /**
     * the initialization of the system from the {@link Configuration} information.
     *
     * @param config The Configuration object to initialize the {@link Renderer} on.
     * @return initialization status (O is ok, negative values are error)
     */
    public int initialize(Configuration config) {
        setViewport(config.width, config.height);
        /*
         * Add the core Render helpers for {@link GameObject}.
         */
        addRenderHelper(new GameObjectRenderHelper(this));
        /*
         * Add the Render helper for the {@link TextObject}.
         */
        addRenderHelper(new TextRenderHelper(this));

        Graphics2D gri = (Graphics2D) buffer.getGraphics();
        /*
         * font used to display debug information.
         */
        Font debugFont = gri.getFont().deriveFont(0.8f);
        /*
         * The Font used to render pause message.
         */
        Font pauseFont = gri.getFont().deriveFont(1.8f);
        return 0;
    }

    /**
     * Render all the objects declared.
     */
    public void draw(int debug) {
        this.debug = debug;
        Graphics2D g = this.buffer.createGraphics();
        g.clearRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
        setRenderingHintsList(g);

        moveFocusToCamera(g, camera, -1);
        drawObjectList(g, objects);
        drawWorld(g, world);
        moveFocusToCamera(g, camera, 1);
        drawObjectList(g, objectsRelativeToCamera);
        drawPauseText(g);

        g.dispose();
        if (renderScreenshot) {
            saveScreenshot();
            renderScreenshot = false;
        }
    }

    /**
     * Draw World influencer list to screen for debug purpose
     *
     * @param g     the Graphics2D api
     * @param world the World object to be drawn for debug purpose (only if debug d <1)
     */
    private void drawWorld(Graphics2D g, World world) {
        if (debug > 1) {
            for (Influencer i : world.influencers) {
                if (debug >= i.debugLevel) {
                    switch (i.area.type) {
                        case RECTANGLE:
                            drawRectangle(g, i.debugLineColor, i.debugFillColor, i.area.shape);
                            break;
                        case CIRCLE:
                            drawEllipse(g, i.debugLineColor, i.debugFillColor, i.area.ellipse);
                            break;
                        default:
                            break;
                    }
                    drawTextWithBackground(g, i.name,
                            i.debugLineColor, i.debugFillColor,
                            i.position.x + i.debugOffsetX, i.position.y + i.debugOffsetY);
                }
            }
            g.setColor(Color.DARK_GRAY);
            for (int y = 0; y < world.height; y += 16) {
                g.drawRect(0, y, (int) world.width, 16);
            }
            for (int x = 0; x < world.width; x += 16) {
                g.drawRect(x, 0, 16, (int) world.height);
            }
        }
    }

    private void drawEllipse(Graphics2D g, Color borderColor, Color fillColor, Ellipse2D.Double ellipse) {
        if (Optional.ofNullable(fillColor).isPresent()) {
            g.setColor(fillColor);
            g.fill(ellipse);
        }
        g.setColor(borderColor);
        g.draw(ellipse);
    }

    public void drawRectangle(Graphics2D g, Color borderColor, Color fillColor, Box shape) {
        if (Optional.ofNullable(fillColor).isPresent()) {
            g.setColor(fillColor);
            g.fillRect((int) shape.x, (int) shape.y, (int) shape.width, (int) shape.height);
        }
        g.setColor(borderColor);
        g.drawRect((int) shape.x, (int) shape.y, (int) shape.width, (int) shape.height);
    }

    /**
     * Draw the pause text if pause is activated.
     *
     * @param g the Graphics API
     * @see Graphics2D
     */
    private void drawPauseText(Graphics2D g) {
        if (game.isPause()) {

            drawTextWithBackground(g,
                    "Game Paused",
                    Color.WHITE,
                    new Color(0.1f, 0.1f, 0.4f, 0.8f),
                    (this.buffer.getWidth()) / 2.0,
                    ((this.buffer.getHeight() / 3.0) * 2.0));
        }
    }

    /**
     * draw text with a border background.
     *
     * @param g               the Graphics API
     * @param pauseText       the text of the pause message
     * @param backgroundColor the color to fill background text
     * @param textColor       the color to render text
     * @param x               horizontal position
     * @param y               vertical position.
     */
    private void drawTextWithBackground(Graphics2D g, String pauseText, Color textColor, Color backgroundColor, double x, double y) {
        g.setColor(backgroundColor);
        g.setFont(g.getFont().deriveFont(16.0f));
        int txtWidth = g.getFontMetrics().stringWidth(pauseText);
        int txtHeight = g.getFontMetrics().getHeight();
        g.fillRect(
                0,
                (int) (y - txtHeight),
                this.buffer.getWidth(),
                txtHeight + 4);
        g.setColor(textColor);
        g.drawString(pauseText, (int) (x - (txtWidth / 2)), (int) y);
    }

    /**
     * Set the default Graphics2D API configuration for rendering purpose.
     *
     * @param g the Graphics2D API for drawing things on rendering buffer
     */
    private void setRenderingHintsList(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    /**
     * Move the camera focus to the camera position.
     *
     * @param g         the Graphics API
     * @param camera    the camera to move the viewport on
     * @param direction value +1 = move to, -1 = move back.
     */
    private void moveFocusToCamera(Graphics2D g, Camera camera, double direction) {
        if (camera != null) {
            g.translate(camera.position.x * direction, camera.position.y * direction);
        }
    }

    /**
     * Draw all the requested objects.
     *
     * @param g       the Graphics API to be used to render all objects.
     * @param objects the list of GameObject to be rendered.
     */
    private void drawObjectList(Graphics2D g, List<GameObject> objects) {
        objects.stream().filter(AbstractEntity::isActive)
                .collect(Collectors.toList())
                .forEach(go -> {
                    draw(g, go);
                    // process child
                    go.getChild().stream()
                            .filter(AbstractEntity::isActive)
                            .collect(Collectors.toList())
                            .forEach(co -> draw(g, co));
                });
    }

    /**
     * Draw the GameObject go with the GraphicsAPI g.
     *
     * @param g  the Graphics API
     * @param go The GameObject to be rendered.
     */
    private void draw(Graphics2D g, GameObject go) {
        String goClazzName = go.getClass().getName();
        if (renderHelpers.containsKey(goClazzName)) {
            RenderHelper rh = renderHelpers.get(goClazzName);
            rh.draw(g, go);
            rh.drawDebugInfo(g, go);
        } else {
            g.setColor(go.color);
            g.drawRect((int) (go.position.x), (int) (go.position.y), (int) (go.width), (int) (go.height));
        }
    }

    /**
     * Request to add a GameObject to the rendering stack.
     *
     * @param go GameObject to be rendered.
     */
    @Override
    public synchronized void add(GameObject go) {
        if (go.relativeToCamera) {
            addAndSortObjectToList(objectsRelativeToCamera, go);
        } else {
            addAndSortObjectToList(objects, go);
        }
    }

    /**
     * SOrt Object to be rendered at camera viewport position
     *
     * @param listObjects the list of object to render.
     * @param go          the GameObject to be added.
     */
    private void addAndSortObjectToList(List<GameObject> listObjects, GameObject go) {
        if (!listObjects.contains(go)) {
            listObjects.add(go);
            listObjects.sort((a, b) -> b.layer <= a.layer ? a.priority < b.priority ? 1 : -1 : 1);
        }
    }

    /**
     * Clear the object rendering stack.
     */
    public void clear() {
        objects.clear();
    }

    /**
     * get the buffer image rendered to be displayed out of this renderer.
     *
     * @return the rendered image buffer.
     */
    public BufferedImage getBuffer() {
        return this.buffer;
    }

    /**
     * Save a screenshot of the current buffer.
     */
    private void saveScreenshot() {
        final String path;//.substring(1);
        path = Objects.requireNonNull(this.getClass().getResource("/")).getPath();
        if (Optional.ofNullable(path).isPresent()) {
            Path targetDir = Paths.get(path + "/screenshots");
            int i = screenShotIndex++;
            String filename = String.format("%sscreenshots/%s-%d.png", path, java.lang.System.nanoTime(), i);

            try {
                if (!Files.exists(targetDir)) {
                    Files.createDirectory(targetDir);
                }
                File out = new File(filename);
                ImageIO.write(getBuffer(), "PNG", out);

                logger.info("Write screenshot to {}", filename);
            } catch (IOException e) {
                logger.error("Unable to write screenshot to {}:{}", filename, e.getMessage());
            }
        }
    }

    /**
     * Add a RenderHelper to te Render to extend its rendering capabilities to other specific Objects.
     *
     * @param rh The {@link RenderHelper} implementation for a dedicated Object type to be added to {@link Renderer} system.
     */
    public void addRenderHelper(RenderHelper<?> rh) {
        renderHelpers.put(rh.getType(), rh);
    }

    /**
     * retrieve the list of RenderHelpers
     *
     * @return the {@link RenderHelper} list.
     */
    public Map<String, RenderHelper<?>> getRenderHelpers() {
        return renderHelpers;
    }

    /**
     * Move viewport focus to the {@link Camera} c.
     *
     * @param c the Camera
     * @return the updated Render system.
     */
    public Renderer moveFocusToCamera(Camera c) {
        camera = c;
        return this;
    }

    /**
     * Set viewport dimension
     *
     * @param w width of the viewport
     * @param h height of the camera
     * @return the update Render object.
     */
    public Renderer setViewport(int w, int h) {
        this.buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        this.viewport = new Dimension(w, h);
        return this;
    }

    @Override
    public void dispose() {
        objects.clear();
        objectsRelativeToCamera.clear();
    }

    @Override
    public boolean isReady() {
        return !renderHelpers.isEmpty() && objectsRelativeToCamera.isEmpty() && objects.isEmpty();
    }

    public void requestScreenShot() {
        renderScreenshot = true;
    }

    public Dimension getViewport() {
        return viewport;
    }

    public Graphics2D getGraphics() {
        return (Graphics2D) buffer.getGraphics();
    }

    public Color getDebugColor() {
        return this.debugColor;
    }

    public void setWorld(World w) {
        this.world = w;
    }

    public void setDebugLevel(int dl) {
        this.debug = dl;
    }

    @Override
    public String getName() {
        return Renderer.class.getName();
    }

    public Camera getCamera() {
        return this.camera;
    }

}
