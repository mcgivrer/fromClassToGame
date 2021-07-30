package fr.snapgames.fromclasstogame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Render {

    private BufferedImage buffer;

    private Map<String, GameObject> objects = new HashMap<>();

    public Render(int width, int height) {

        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void render() {
        Graphics2D g = this.buffer.createGraphics();
        g.clearRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
        for (Entry<String, GameObject> go : objects.entrySet()) {
            draw(g, go.getValue());
        }

        g.dispose();
    }

    private void draw(Graphics2D g, GameObject go) {
        g.setColor(go.color);
        g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
    }

    public BufferedImage getBuffer() {
        return this.buffer;
    }

    public Render add(GameObject go) {
        if (!objects.containsKey(go.name)) {
            objects.put(go.name, go);
        }
        return this;
    }

}
