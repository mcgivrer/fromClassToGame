package fr.snapgames.fromclasstogame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window {

    private JFrame frame;
    private Font debugFont;
    private int debug = 0;

    public Window(String title, int width, int height) {
        setFrame(title, width, height);
    }

    public Window setFrame(String title, int width, int height) {
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
        return this;
    }

    public void draw(long realFPS, BufferedImage img) {
        Graphics2D g = (Graphics2D) frame.getGraphics();
        if (debugFont == null) {
            debugFont = g.getFont().deriveFont(Font.CENTER_BASELINE, 11);
        }
        g.drawImage(img, 0, 30, frame.getWidth(), frame.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);

        if (this.debug > 0) {
            g.setFont(debugFont);
            g.setColor(Color.ORANGE);
            g.drawString(String.format("[ DBG:%1d | FPS:%03d ]", this.debug, realFPS), 10, frame.getHeight() - 20);
            g.dispose();
        }
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public void setDebug(int d) {
        this.debug = d;
    }

}
