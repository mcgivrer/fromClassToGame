package fr.snapgames.fromclasstogame.core.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Render {

    private static final Logger logger = LoggerFactory.getLogger(Render.class);

    private BufferedImage buffer;
    private static int screenShotIndex = 0;

    private Camera camera;

    private List<GameObject> objects = new ArrayList<>();
    private List<GameObject> objectsRelativeToCamera = new ArrayList<>();
    private Map<String, RenderHelper> renderHelpers = new HashMap<>();
    private Dimension viewport;
    private Color debugColor = Color.ORANGE;
    private int debug = 0;
    private World world;

    public Render(int width, int height) {
        setViewport(width, height);
        addRenderHelper(new GameObjectRenderHelper());
        addRenderHelper(new TextRenderHelper());
    }

    public void render() {
        Graphics2D g = this.buffer.createGraphics();
        g.clearRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (camera != null) {
            g.translate(-camera.x, -camera.y);
        }
        renderWorld(g, world);
        for (GameObject go : objects) {
            draw(g, go);
        }

        if (camera != null) {
            g.translate(camera.x, camera.y);
        }
        for (GameObject go : objectsRelativeToCamera) {
            draw(g, go);
        }

        g.dispose();
    }

    private void renderWorld(Graphics2D g, World w) {
        if (w != null && debug > 0) {
            g.setColor(Color.BLUE);
            for (int x = 0; x < w.width; x += 16) {
                g.drawRect(x, 0, (int) 16, (int) w.height);
            }
            for (int y = 0; y < w.height; y += 16) {
                if (y + 16 < w.height) {
                    g.drawRect(0, y, (int) w.width, 16);
                }
            }
            g.setColor(debugColor);
            g.drawRect(0, 0, (int) w.width, (int) w.height);
        }
    }

    private void draw(Graphics2D g, GameObject go) {
        String goClazzName = go.getClass().getName();
        if (renderHelpers.containsKey(goClazzName)) {
            RenderHelper rh = renderHelpers.get(goClazzName);
            rh.draw(g, go);
        } else {
            g.setColor(go.color);
            g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
        }
    }

    public Render add(GameObject go) {
        if (go.relativeToCamera) {
            addAndSortObjectToList(objectsRelativeToCamera, go);
        } else {
            addAndSortObjectToList(objects, go);
        }
        return this;
    }

    private void addAndSortObjectToList(List<GameObject> listObjects, GameObject go) {
        if (!listObjects.contains(go)) {
            listObjects.add(go);
            listObjects.sort((a, b) -> {
                return a.layer < b.layer ? -1 : a.priority < b.priority ? -1 : 1;
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
    public void saveScreenshot() {
        final String path = this.getClass().getResource("/").getPath().substring(1);
        Path targetDir = Paths.get(path + "/screenshots");
        int i = screenShotIndex++;
        String filename = String.format("%sscreenshots/%s-%d.png", path, System.nanoTime(), i);

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

    public void addRenderHelper(RenderHelper rh) {
        renderHelpers.put(rh.getType(), rh);
    }

    public Map<String, RenderHelper> getRenderHelpers() {
        return renderHelpers;
    }

    public Render setCamera(Camera c) {
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
}
