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

public class Render extends System {

    private static final Logger logger = LoggerFactory.getLogger(Render.class);
    private static int screenShotIndex = 0;
    private BufferedImage buffer;
    private Camera camera;

    private List<GameObject> objectsRelativeToCamera = new CopyOnWriteArrayList<>();
    private Map<String, RenderHelper<?>> renderHelpers = new HashMap<>();
    private Dimension viewport;
    private Color debugColor = Color.ORANGE;
    private int debug = 0;
    private World world;
    private boolean renderScreenshot = false;

    private Font debugFont;
    private Font pauseFont;

    public Render(Game g) {
        super(g);
    }

    public int initialize(Configuration config) {
        setViewport(config.width, config.height);
        addRenderHelper(new GameObjectRenderHelper(this));
        addRenderHelper(new TextRenderHelper(this));
        addRenderHelper(new DebugViewportGridRenderHelper(this));
        Graphics2D gri = (Graphics2D) buffer.getGraphics();
        debugFont = gri.getFont().deriveFont(0.8f);
        pauseFont = gri.getFont().deriveFont(1.8f);
        return 0;
    }

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

    private void renderWorld() {

    }

    private void drawPauseText(Graphics2D g) {
        if (game.isPause()) {

            drawTextWithBackground(g,
                    "Game Paused",
                    new Color(0.1f, 0.1f, 0.4f, 0.8f),
                    (this.buffer.getWidth()) / 2,
                    ((this.buffer.getHeight() / 3) * 2));
        }
    }

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

    private void setRenderingHintsList(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private void moveFocusToCamera(Graphics2D g, Camera camera, double direction) {
        if (camera != null) {
            g.translate(camera.position.x * direction, camera.position.y * direction);
        }
    }


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

    @Override
    public synchronized void add(GameObject go) {
        if (go.relativeToCamera) {
            addAndSortObjectToList(objectsRelativeToCamera, go);
        } else {
            addAndSortObjectToList(objects, go);
        }
    }

    private void addAndSortObjectToList(List<GameObject> listObjects, GameObject go) {
        if (!listObjects.contains(go)) {
            listObjects.add(go);
            listObjects.sort((a, b) -> {
                return a.layer < b.layer ? 1 : a.priority < b.priority ? 1 : -1;
            });
        }
    }

    public void clear() {
        objects.clear();
    }

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

    public void addRenderHelper(RenderHelper<?> rh) {
        renderHelpers.put(rh.getType(), rh);
    }

    public Map<String, RenderHelper<?>> getRenderHelpers() {
        return renderHelpers;
    }

    public Render moveFocusToCamera(Camera c) {
        camera = c;
        return this;
    }

    public Render setViewport(int w, int h) {
        this.buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        this.viewport = new Dimension(w, h);
        return this;
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
}
