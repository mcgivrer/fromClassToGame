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
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

/**
 * <p>The {@link Window} Component is bringing the front display to our game.</p>
 * <p>It creates the link between the {@link Render} and the OS through the JVM graphics capabilities
 * to be able to show the game's screen.</p>
 * <p>The main usage is:</p>
 *
 * <pre>
 * public class Game{
 *   Configuration config;
 *   Render render;
 *   Window win;
 *   public Game(){
 *     config = new Configuration(this);
 *     render = new Render(this);
 *     win = new Window(this, config);
 *   }
 *   //...
 * }
 * </pre>
 * <p>
 * And later in the main loop, after rendering things to an internal buffer:
 * </p>
 *
 * <pre>
 * public class Game{
 *   //...
 *   public render(){
 *     render.renderAllMyObjects);
 *     win.draw(this, render.getBuffer());
 *   }
 *   //...
 * }
 * </pre>
 *
 * @author Frédéric Delorme
 * @since 1.0.0
 */
public class Window extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(Window.class);

    private Configuration config;
    private GraphicsDevice currentDevice;
    private Dimension dim;
    private float ratio;
    private Font debugFont;
    private int debug = 0;

    private boolean fullscreen;
    private int defaultScreen = -1;
    private int currentScreen = 0;

    private JFrame frame;

    // Debug information on the debug status line
    private Map<String, String> debugElementInfo = new HashMap<>();

    /// list of attached event listener to his window.
    List<EventListener> listenersBck = new ArrayList<>();

    /**
     * Only available for test purpose.
     * Create a new Window for the game, with the corresponding <code>title</code>, <code>width</code> and <code>height</code> characteristics.
     *
     * @param title
     * @param width
     * @param height
     */
    @Deprecated
    public Window(String title, int width, int height) {
        setFrame(title, width, height);
        defaultScreen = -1;
    }

    /**
     * Create a window for the game, according to configuration file.
     *
     * @param config
     */
    public Window(Configuration config) {
        super(new GridLayout());
        this.config = config;
        createFrame();
        defaultScreen = config.defaultScreen;
    }

    /**
     * Create the internal JFrame with the internally already defined Configuration object.
     *
     * @return the  Window with the new JFrame based on configuration.
     */
    public Window createFrame() {
        setFrame(config.title, (int) (config.width * config.scale), (int) (config.height * config.scale));
        return this;
    }

    /**
     * <p>Create the Window  and its JFrame according <code>title</code>, <code>width</code> and <code>height</code>,
     * and to the contextual fullScreen mode (see {@link Window#isFullScreen()}.</p>
     * <p>The frame will be created with or without the Title bar.</p>
     *
     * @param title  title of the window
     * @param width  width of the playable area part of the window
     * @param height height of the playable area part of the window
     * @return the Window with its newly initialized frame.
     */
    public Window setFrame(String title, int width, int height) {
        frame = new JFrame(title);
        try {
            frame.setIconImage(ResourceManager.getImage("images/logo/sg-logo-image.png"));
        } catch (UnknownResource e) {
            logger.warn("unable to read window icon from 'images/logo/sg-logo-image.png'");
        }
        currentDevice = getGraphicsDevice(currentScreen);
        DisplayMode dm = currentDevice.getDisplayMode();
        ratio = (float) ((1.0f * dm.getWidth()) / (1.0f * dm.getHeight()));

        Insets ins = frame.getContentPane().getInsets();
        dim = new Dimension(width, height + ins.top + ins.right);
        //frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // define Window content and size.
        frame.setLayout(new GridLayout());
        getLayout().layoutContainer(frame);

        frame.setContentPane(this);
        frame.getContentPane().setPreferredSize(dim);

        setSize(dim);
        setMaximumSize(dim);
        setMinimumSize(dim);
        setBackground(Color.BLACK);

        frame.setIgnoreRepaint(true);
        frame.enableInputMethods(true);
        frame.setFocusTraversalKeysEnabled(false);

        frame.setLocationByPlatform(false);
        frame.setLocation(((currentDevice.getDisplayMode().getWidth() - dim.width) / 2), ((currentDevice.getDisplayMode().getHeight() - dim.height) / 2));
        logger.info("display at ({},{})",
                (currentDevice.getDisplayMode().getWidth() - dim.width / 2),
                (currentDevice.getDisplayMode().getHeight() - dim.height / 2));
        //frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setUndecorated(isFullScreen());
        frame.pack();
        frame.setVisible(true);
        if (frame.getBufferStrategy() == null) {
            frame.createBufferStrategy(2);
        }
        return this;
    }

    /**
     * Request to draw this img to the JPanel content on the Window.
     *
     * @param realFPS the game computed frame rate to be displayed if necessary.
     * @param img     the BufferedImage to be rendered on the window.
     */
    public void draw(long realFPS, BufferedImage img) {
        Graphics2D g = (Graphics2D) getGraphics();
        if (frame.getBufferStrategy() != null) {
            if (frame.getBufferStrategy().getDrawGraphics() == null) {
                return;
            }
            g = (Graphics2D) frame.getBufferStrategy().getDrawGraphics();

        }
        if (g != null && frame.isDisplayable()) {
            int yPos = !isFullScreen() ? 32 : 0;
            g.scale(1.0, ((float) getWidth() / getHeight()) / ratio);
            g.drawImage(img, 0, yPos, getWidth() + 10, getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);
            if (debugFont == null) {
                debugFont = g.getFont().deriveFont(Font.CENTER_BASELINE, 11);
            }
            g.scale(1.0, 1.0);
            drawDebugStatusInfo(realFPS, g);
            g.dispose();
            if (frame.getBufferStrategy() != null) {
                frame.getBufferStrategy().show();
            }
        }
    }

    /**
     * If debug activated on this Window (see {@link Window#debug}, a debug area is added at the display bottom.
     * This debug display area can be extended by adding some entry to the {@link Window#debugElementInfo}.
     *
     * @param realFPS
     * @param g
     */
    private void drawDebugStatusInfo(long realFPS, Graphics2D g) {
        if (this.debug > 0) {
            g.setFont(debugFont);
            int nbObjs = SystemManager.getNbObjects();

            FontMetrics fm = g.getFontMetrics();
            double gravity = ((PhysicEngine) SystemManager.get(PhysicEngine.class)).getWorld().gravity.y;

            String debugStatusLine = String.format("DBG:%1d | FPS:%03d | nbObj:%03d | g: %1.3f",
                    this.debug, realFPS, nbObjs, gravity);

            for (Map.Entry<String, String> e : debugElementInfo.entrySet()) {
                debugStatusLine = debugStatusLine + " | " + e.getKey() + ":" + e.getValue();
            }

            g.setColor(new Color(0.3f, 0.0f, 0.0f, 0.6f));
            g.fillRect(0, getHeight() - (20 + fm.getHeight()), getWidth(), fm.getHeight() + 4);

            g.setColor(Color.ORANGE);
            g.drawString("[ " + debugStatusLine + " ]", 10, getHeight() - 20);
        }
    }


    /**
     * return the JFrame for this Window.
     *
     * @return
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Request to close this Window frame.
     */
    public void close() {
        dispatchEvent(new WindowEvent(getFrame(), WindowEvent.WINDOW_CLOSING));
        frame.dispose();
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
        if (currentDevice != null && currentDevice.isFullScreenSupported()) {
            setVisible(false);
            frame.dispose();
            if (!fullscreen) {
                fullscreen = true;
                createFrame();
                currentDevice.setFullScreenWindow(frame);
            } else {
                fullscreen = false;
                createFrame();
                currentDevice.setFullScreenWindow(null);
            }
            restoreListeners();
            setVisible(true);
        }
    }


    /**
     * restore list of connected event listener by adding them to the frame.
     */
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
            setVisible(true);

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
            logger.debug("List of connected display devices : ");
            for (GraphicsDevice d : devs) {
                logger.debug(String.format("%s: (%d x %d)", d.getIDstring(), d.getDisplayMode().getWidth(), d.getDisplayMode().getHeight()));
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

    /**
     * get Number of available Screen devices
     *
     * @return
     */
    public int getMaxScreen() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
    }

    /**
     * Return true if this window in full srceen mode.
     *
     * @return
     */
    public boolean isFullScreen() {
        return fullscreen;
    }

    /**
     * return debug state value.
     *
     * @return
     */
    public int getDebug() {
        return debug;
    }

    /**
     * Set debug mode for this Window.
     *
     * @param d
     */
    public void setDebug(int d) {
        this.debug = d;
    }

    /**
     * return current active ScreenDevice.
     *
     * @return
     */
    public GraphicsDevice getScreenDevice() {
        return currentDevice;
    }

    /**
     * Add a {@link KeyListener} <code>listener</code> to the Window {@ink JFrame}.
     * this lister is also added to the backup event listener to be able to restore this list
     * if frame is changed/re-created.
     *
     * @param listener the {@link KeyListener} to be added to the Window event listener's list.
     * @see JFrame#addKeyListener(KeyListener)
     */
    public void addListener(KeyListener listener) {
        this.listenersBck.add(listener);
        frame.addKeyListener(listener);
    }

    /**
     * Force a new {@link Configuration} for this {@link Window}.
     *
     * @param configuration the new {@link Configuration} object to be attached to the Window instance.
     */
    public void setConfiguration(Configuration configuration) {
        this.config = configuration;
    }
}
