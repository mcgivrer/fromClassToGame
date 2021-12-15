package fr.snapgames.fromclasstogame.core.gfx;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.renderer.DebugViewportGridRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.GameObjectRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.TextRenderHelper;
import fr.snapgames.fromclasstogame.core.io.I18n;
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


    private Graphics2D g;

    public Render(Game game) {
        super(game);
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
        start();
        moveFocusToCamera(camera, -1);
        drawObjectList(objects);
        moveFocusToCamera(camera, 1);
        drawObjectList(objectsRelativeToCamera);
        drawPauseText();
        end();
    }

    private void start() {
        g = this.buffer.createGraphics();
        g.clearRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
        setRenderingHintsList();
    }

    private void end() {
        g.dispose();
        if (renderScreenshot) {
            saveScreenshot();
            renderScreenshot = false;
        }
    }

    private void drawPauseText() {
        if (game.isPause()) {

            drawTextWithBackground(
                    "Game Paused",
                    new Color(0.1f, 0.1f, 0.4f, 0.8f),
                    (this.buffer.getWidth()) / 2,
                    ((this.buffer.getHeight() / 3) * 2));
        }
    }

    private void drawTextWithBackground(String pauseText, Color color, double x, double y) {
        g.setColor(color);
        g.setFont(g.getFont().deriveFont(16.0f));
        int txtWidth = g.getFontMetrics().stringWidth(pauseText);
        int txtHeight = g.getFontMetrics().getHeight();
        g.fillRect(
                0,
                (int) (y - txtHeight),
                this.buffer.getWidth(),
                txtHeight + 4);

        drawTextWithShadow(I18n.getMessage("game.pause.text"), x, y, Color.WHITE, 4, Color.BLACK);
    }


    private void drawText(String text, double posX, double posY, Color textColor) {
        int txtWidth = g.getFontMetrics().stringWidth(text);
        int txtHeight = g.getFontMetrics().getHeight();
        g.setColor(textColor);
        g.drawString(text, (int) (posX - (txtWidth / 2)), (int) posY);
    }

    private void drawTextWithShadow(String text, double posX, double posY, Color textColor, int borderSize, Color borderColor) {
        for (int dx = -borderSize / 2; dx <= borderSize / 2; dx++) {
            for (int dy = -borderSize / 2; dy <= borderSize / 2; dy++) {
                drawText(text, posX + dx, posY + dy, borderColor);
            }
        }
        drawText(text, posX, posY, textColor);
    }

    private void setRenderingHintsList() {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private void moveFocusToCamera(Camera camera, double direction) {
        if (camera != null) {
            g.translate(camera.position.x * direction, camera.position.y * direction);
        }
    }


    private void drawObjectList(List<GameObject> objects) {
        objects.stream().filter(f -> f.active)
                .collect(Collectors.toList())
                .forEach(go -> {
                    draw(go);
                    // process child
                    go.getChild().stream()
                            .filter(c -> c.active)
                            .collect(Collectors.toList())
                            .forEach(co -> draw(co));
                });
    }


    /*private void drawObjectList(Graphics2D g, List<GameObject> objects) {
        for (GameObject go : objects) {
            if (go.active) {
                draw(g, go);
                go.getChild().forEach(co -> draw(g, co));
            }
        }
    }*/

    private void draw(GameObject go) {
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
