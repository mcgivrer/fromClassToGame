package fr.snapgames.fromclasstogame.core.gfx;

import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.physic.PhysicEngine;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

public class Window {

    private static final Logger logger = LoggerFactory.getLogger(Window.class);

    private Configuration config;
    private GraphicsDevice currentDevice;
    private Dimension dim;
    private JFrame frame;

    private Font debugFont;
    private int debug = 0;

    private boolean fullscreen;
    private int defaultScreen = -1;
    private int currentScreen = 0;

    // Debug information on the debug status line
    private Map<String, String> debugElementInfo = new HashMap<>();

    List<EventListener> listenersBck = new ArrayList<>();

    @Deprecated
    public Window(String title, int width, int height) {
        setFrame(title, width, height);
        defaultScreen = -1;
    }

    public Window(Configuration config) {
        this.config = config;
        createFrame();
        defaultScreen = config.defaultScreen;
    }

    public Window createFrame() {
        setFrame(config.title, (int) (config.width * config.scale), (int) (config.height * config.scale));
        return this;
    }


    public Window setFrame(String title, int width, int height) {
        frame = new JFrame(title);

        try {
            frame.setIconImage(ResourceManager.getImage("images/logo/sg-logo-image.png"));
        } catch (UnknownResource e) {
            logger.warn("unable to read window icon from 'images/logo/sg-logo-image.png'");
        }
        currentDevice = getGraphicsDevice(currentScreen);
        Insets ins = frame.getContentPane().getInsets();
        dim = new Dimension(width, height + ins.top);
        frame.setLayout(null);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(dim);
        frame.setPreferredSize(dim);
        frame.setMaximumSize(dim);
        frame.setIgnoreRepaint(true);
        frame.enableInputMethods(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setUndecorated(isFullScreen());
        frame.pack();
        frame.setVisible(true);
        BufferStrategy bs = frame.getBufferStrategy();
        if (bs == null) {
            frame.createBufferStrategy(2);
        }
        return this;
    }

    public void draw(long realFPS, BufferedImage img) {
        if (frame != null) {
            BufferStrategy bs = frame.getBufferStrategy();
            if (bs != null && bs.getDrawGraphics() != null) {
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();

                if (debugFont == null) {
                    debugFont = g.getFont().deriveFont(Font.CENTER_BASELINE, 11);
                }
                int menuHeight = isFullScreen() ? 0 : 30;
                g.drawImage(img, 0, menuHeight, frame.getWidth(), frame.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);

                drawDebugStatusInfo(realFPS, g);
                g.dispose();
                bs.show();
            }
        }
    }

    private void drawDebugStatusInfo(long realFPS, Graphics2D g) {
        if (this.debug > 0) {
            g.setFont(debugFont);
            g.setColor(Color.ORANGE);
            int nbObjs = SystemManager.getNbObjects();
            double gravity = ((PhysicEngine) SystemManager.get(PhysicEngine.class)).getWorld().gravity.y;
            String debugStatusLine = String.format("DBG:%1d | FPS:%03d | nbObj:%03d | g: %1.3f",
                    this.debug, realFPS, nbObjs, gravity);
            for (Map.Entry<String, String> e : debugElementInfo.entrySet()) {
                debugStatusLine = debugStatusLine + " | " + e.getKey() + ":" + e.getValue();
            }
            g.drawString("[ " + debugStatusLine + " ]", 10, frame.getHeight() - 20);
        }
    }


    public JFrame getFrame() {
        return this.frame;
    }

    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }


    /**
     * Set Default Screen to display the window in full Screen.
     *
     * @param defScreen the value of the device number to be used as destination of the window fullscreen.
     *                  (min=0, max corresponding to the number of screen connected to the computer).
     */
    public void setScreen(int defScreen) {
        currentScreen = defScreen;
        if (fullscreen && defScreen < getMaxScreen()) {
            currentDevice = getGraphicsDevice(currentScreen);
            currentDevice.setFullScreenWindow(frame);
        }
    }

    /**
     * Switching from windowed to fullscreen.
     */
    public void switchFullScreen() {
        currentDevice = getGraphicsDevice(currentScreen);
        if (!fullscreen && currentDevice.isFullScreenSupported()) {
            fullscreen = true;
            frame.setVisible(false);
            frame.dispose();
            createFrame();
            currentDevice.setFullScreenWindow(frame);
            frame.setVisible(true);
            restoreListeners();
        } else {
            fullscreen = false;
            frame.setVisible(false);
            frame.dispose();
            createFrame();
            currentDevice.setFullScreenWindow(null);
            frame.setVisible(true);
            restoreListeners();
        }
    }

    private void restoreListeners() {
        listenersBck.stream().forEach(l -> frame.addKeyListener((KeyListener) l));
    }

    /**
     * Switch between connected Screens when window is in fullscreen mode.
     */
    public void switchScreen() {

        if (fullscreen) {
            if (currentScreen + 1 < getMaxScreen()) {
                currentScreen += 1;
            } else {
                currentScreen = 0;
            }

            currentDevice.setFullScreenWindow(null);
            frame.setVisible(true);

            currentDevice = getGraphicsDevice(currentScreen);
            currentDevice.setFullScreenWindow(frame);
        }
    }

    /**
     * get the GraphicDevice corresponding to the currentScreen connected screen.
     *
     * @param currentScreen the screen to be activated
     * @return the GraphicDevice corresponding the ht currentScreen number.
     */
    private GraphicsDevice getGraphicsDevice(int currentScreen) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = null;
        if (currentScreen == -1) {
            device = env.getDefaultScreenDevice();
        } else {
            GraphicsDevice[] devs = env.getScreenDevices();
            logger.info("list of devices : ");
            for (GraphicsDevice d : devs) {
                logger.info(String.format("%s: (%d x %d)", d.getIDstring(), d.getDisplayMode().getWidth(), d.getDisplayMode().getHeight()));
            }
            assert (currentScreen >= 0 && currentScreen < devs.length);
            device = devs[currentScreen];
        }
        return device;
    }

    /**
     * Add a new Debug info element, named `elementName` on the debug status line a,d having the `elementValue` value.
     *
     * @param elementName  name of this element to be displayed on the status line
     * @param elementValue Value of this debug element on the status line
     */
    public void addDebugStatusElement(String elementName, String elementValue) {
        this.debugElementInfo.put(elementName, elementValue);
    }

    public int getMaxScreen() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
    }

    public boolean isFullScreen() {
        return fullscreen;
    }

    public int getDebug() {
        return debug;
    }

    public void setDebug(int d) {
        this.debug = d;
    }

    public GraphicsDevice getScreenDevice() {
        return currentDevice;
    }

    public void addListener(KeyListener listener) {
        this.listenersBck.add(listener);
        frame.addKeyListener(listener);
    }
}
