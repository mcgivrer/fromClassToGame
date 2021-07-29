package fr.snapgames.fromclasstogame;

import javax.swing.JFrame;

import java.awt.GraphicsDevice;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Point;

public class Window {

    private JFrame frame;

    public Window(String title, int width, int height) {
        frame = new JFrame(title);

        GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
        Dimension dim = new Dimension(width, height);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(dim);
        frame.setPreferredSize(dim);
        frame.setMaximumSize(dim);
        frame.setLocation(new Point((int) (device.getDisplayMode().getWidth() - dim.width) / 2,
                (int) (device.getDisplayMode().getHeight() - dim.height) / 2));
        frame.pack();
        frame.setVisible(true);
    }

    public void draw(BufferedImage img) {
        frame.getGraphics().drawImage(img, 0, 0, frame.getWidth(), frame.getHeight(), 0, 0, img.getWidth(),
                img.getHeight(), null);
    }

}
