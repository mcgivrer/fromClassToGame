package fr.snapgames.fromclasstogame.core.gfx;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.TextObject;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Render {

    private BufferedImage buffer;
    static int screenShotIndex = 0;

    private List<GameObject> objects = new ArrayList<>();

    public Render(int width, int height) {
        setViewport(width, height);
    }

    public Render setViewport(int w, int h) {
        this.buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        return this;
    }

    public void render() {
        Graphics2D g = this.buffer.createGraphics();
        g.clearRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        for (GameObject go : objects) {
            draw(g, go);
        }
        g.dispose();
    }

    private void draw(Graphics2D g, GameObject go) {
        g.setColor(go.color);
        String goClazzName = go.getClass().getName();
        if (GameObject.class.getName().equals(goClazzName)) {
            if (go.image != null) {
                g.drawImage(go.image, (int) (go.x), (int) (go.y), null);
            } else {
                g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
            }
        } else if (TextObject.class.getName().equals(goClazzName)) {
            TextObject to = (TextObject) go;
            g.setFont(to.font);
            g.drawString(to.text, (int) (to.x), (int) (to.y));

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

}
