package fr.snapgames.fromclasstogame;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Render {

    private BufferedImage buffer;

    public Render(int width, int height) {

        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void render() {
        Graphics2D g = this.buffer.createGraphics();
        g.clearRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
        g.dispose();
    }

    public BufferedImage getBuffer() {
        return this.buffer;
    }

}
