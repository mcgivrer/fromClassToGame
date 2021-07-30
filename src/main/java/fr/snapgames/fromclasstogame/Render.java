package fr.snapgames.fromclasstogame;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Render {

    private BufferedImage buffer;

    private List<GameObject> objects = new ArrayList<>();

    public Render(int width, int height) {

        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void render() {
        Graphics2D g = this.buffer.createGraphics();
        g.clearRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        for (GameObject go : objects) {
            draw(g, go);
        }
        g.dispose();
    }

    private void draw(Graphics2D g, GameObject go) {
        g.setColor(go.color);
        if (go.image != null) {
            g.drawImage(go.image, (int) (go.x), (int) (go.y), null);
        } else {
            g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
        }
    }

    public BufferedImage getBuffer() {
        return this.buffer;
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

}
