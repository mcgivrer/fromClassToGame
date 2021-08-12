package fr.snapgames.fromclasstogame.core.gfx;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.imageio.ImageIO;

import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;

public class Render {

    private BufferedImage buffer;
    private static int screenShotIndex = 0;

    private Camera camera;

    private List<GameObject> objects = new ArrayList<>();
    private Map<String, RenderHelper> renderHelpers = new HashMap<>();

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

        for (GameObject go : objects) {
            draw(g, go);
        }

        if (camera != null) {
            g.translate(camera.x, camera.y);
        }

        g.dispose();
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
        if (!objects.contains(go)) {
            objects.add(go);
            objects.sort((a, b) -> {
                return a.priority < b.priority ? -1 : 1;
            });
        }
        return this;
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

            System.out.println(String.format("Write screenshot to %s", filename));
        } catch (IOException e) {
            System.err.println(String.format("Unable to write screenshot to %s:%s", filename, e.getMessage()));
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
        return this;
    }

}
