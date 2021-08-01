package fr.snapgames.fromclasstogame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window {

    private JFrame frame;
    private Font debugFont;

    public Window(String title, int width, int height) {
        frame = new JFrame(title);

        GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
        Insets ins = frame.getContentPane().getInsets();
        Dimension dim = new Dimension(width, height + ins.top);
        frame.setLayout(null);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(dim);
        frame.setPreferredSize(dim);
        frame.setMaximumSize(dim);
        frame.setLocation(new Point((int) (device.getDisplayMode().getWidth() - dim.width) / 2,
                (int) (device.getDisplayMode().getHeight() - dim.height) / 2));
        frame.pack();
        frame.setVisible(true);
    }

    public void draw(long realFPS, BufferedImage img) {
        Graphics2D g = (Graphics2D) frame.getGraphics();
        if (debugFont == null) {
            debugFont = g.getFont().deriveFont(Font.CENTER_BASELINE, 8);
        }
        g.drawImage(img, 0, 30, frame.getWidth(), frame.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);

        g.setFont(debugFont);
        g.setColor(Color.ORANGE);
        g.drawString(String.format("FPS:%03d", realFPS), 10, frame.getHeight() - 30);
        g.dispose();
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

}
