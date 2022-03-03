package fr.snapgames.fromclasstogame.core.gfx;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.renderer.DebugViewportGridRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.GameObjectRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.TextRenderHelper;
import fr.snapgames.fromclasstogame.core.physic.World;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * The Render system ti draw every thing the GameObject needs to.
 *
 * @author Frédéric Delorme
 * @since 0.0.1
 */
public class Render extends System {

    private static final Logger logger = LoggerFactory.getLogger(Render.class);
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
    private List<GameObject> objectsRelativeToCamera = new CopyOnWriteArrayList<>();
    /**
     * Internal ist of Render helpers
     */
    private Map<String, RenderHelper<?>> renderHelpers = new HashMap<>();

    /**
     * debug color to display debug information
     */
    private Color debugColor = Color.ORANGE;
    /**
     * Debug level.
     */
    private int debug = 0;
    /**
     * font used to display debug information.
     */
    private Font debugFont;

    /**
     * The Font used to render pause message.
     */
    private Font pauseFont;

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
    public Render(Game g) {
        super(g);
    }

    /**
     * the initialization of the system from the {@link Configuration} information.
     *
     * @param config The Configuration object to initialize the {@link Render} on.
     * @return
     */
    public int initialize(Configuration config) {
        setViewport(config.width, config.height);
        /**
         * Add the core Render helpers for {@link GameObject}.
         */
        addRenderHelper(new GameObjectRenderHelper(this));
        /**
         * Add the Render helper for the {@link TextObject}.
         */
        addRenderHelper(new TextRenderHelper(this));
        /**
         * Add the Render helper to draw debug information about viewport.
         */
        addRenderHelper(new DebugViewportGridRenderHelper(this));
        Graphics2D gri = (Graphics2D) buffer.getGraphics();
        debugFont = gri.getFont().deriveFont(0.8f);
        pauseFont = gri.getFont().deriveFont(1.8f);
        return 0;
    }

    /**
     * Render all the objects declared.
     */
    public void render() {
        Graphics2D g = this.buffer.createGraphics();
        g.clearRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
        setRenderingHintsList(g);

        moveFocusToCamera(g, camera, -1);
        drawObjectList(g, objects);
        moveFocusToCamera(g, camera, 1);
        drawObjectList(g, objectsRelativeToCamera);
        renderWorld();
        drawPauseText(g);

        g.dispose();
        if (renderScreenshot) {
            saveScreenshot();
            renderScreenshot = false;
        }
    }

    /**
     * Render the world details but only for debug purpose.
     */
    private void renderWorld() {
        // TODO: need to implement the debug rendering to World.
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
                    new Color(0.1f, 0.1f, 0.4f, 0.8f),
                    (this.buffer.getWidth()) / 2,
                    ((this.buffer.getHeight() / 3) * 2));
        }
    }

    /**
     * draw text with a border background.
     *
     * @param g         the Graphics API
     * @param pauseText the text of the pause message
     * @param color     the color to render text
     * @param x         horizontal position
     * @param y         vertical position.
     */
    private void drawTextWithBackground(Graphics2D g, String pauseText, Color color, double x, double y) {
        g.setColor(color);
        g.setFont(g.getFont().deriveFont(16.0f));
        int txtWidth = g.getFontMetrics().stringWidth(pauseText);
        int txtHeight = g.getFontMetrics().getHeight();
        g.fillRect(
                0,
                (int) (y - txtHeight),
                this.buffer.getWidth(),
                txtHeight + 4);
        g.setColor(Color.WHITE);
        g.drawString("Game Paused", (int) (x - (txtWidth / 2)), (int) y);
    }

    /**
     * Set the default Graphics2D API configuration for rendering purpose.
     *
     * @param g
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
        objects.stream().filter(f -> f.isActive())
                .collect(Collectors.toList())
                .forEach(go -> {
                    draw(g, go);
                    // process child
                    go.getChild().stream()
                            .filter(c -> c.isActive())
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
            listObjects.sort((a, b) -> {
                return a.layer < b.layer ? 1 : a.priority < b.priority ? 1 : -1;
            });
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
        final String path = this.getClass().getResource("/").getPath().substring(1);

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

    /**
     * Add a RenderHelper to te Render to extend its rendering capabilities to other specific Objects.
     *
     * @param rh
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
    public Render moveFocusToCamera(Camera c) {
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
    public Render setViewport(int w, int h) {
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
        return Render.class.getName();
    }

    public Camera getCamera() {
        return this.camera;
    }

}
