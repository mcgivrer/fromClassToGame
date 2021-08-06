package fr.snapgames.fromclasstogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.snapgames.fromclasstogame.exceptions.UnknownArgumentException;

/**
 * Project: From Class To Game
 * <p>
 * A First class to build a game attempt.
 *
 * @author Frédéric Delorme<frederic.delorme@gmail.com>
 * @since 0.0.1
 */
public class Game implements KeyListener {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private long realFPS = 60;

    private Window window;
    private Render renderer = new Render(320, 200);
    private InputHandler inputHandler;
    private SceneManager sceneManager;
    private Configuration configuration;

    public boolean exit = false;
    public boolean testMode = false;

    /**
     * the mandatory default constructor
     */
    public Game() {
        this("config");
    }

    /**
     * A constructure mainly used for test purpose.
     */
    public Game(String configPath) {
        configuration = new Configuration(configPath);
    }

    /**
     * Initialize a game with some attributes.
     *
     * @param t title for the game window
     * @param w width of the game window
     * @param h heigth of the game window
     */
    public Game(String t, int w, int h) {
        this("config");
        configuration.title = t;
        configuration.width = w;
        configuration.height = h;
    }

    /**
     * Initialization of the display window and everything the game will need.
     */
    public void initialize(String[] argv) throws UnknownArgumentException {

        configuration.parseArgs(argv);

        renderer = new Render(configuration.width, configuration.height);

        window = new Window(configuration.title, (int) (configuration.width * configuration.scale),
                (int) (configuration.height * configuration.scale));

        inputHandler = new InputHandler(window);
        inputHandler.addKeyListener(this);

        sceneManager = new SceneManager(this);
        sceneManager.initialize(configuration.scenes.split(","));
    }

    /**
     * Entrypoint for the game. can parse the argc from the java command line.
     *
     * @throws UnknownArgumentException
     */
    public void run(String[] argv) throws UnknownArgumentException {
        initialize(argv);
        createScene();
        loop();
        if (!testMode) {
            dispose();
        }
    }

    private void createScene() {
        sceneManager.activate();
        inputHandler.addKeyListener(sceneManager.getCurrent());
    }

    /**
     * the famous main game loop where everything happend.
     */
    private void loop() {
        long start = System.currentTimeMillis();
        long previous = start;
        long dt = 0;
        long frames = 0;
        long timeFrame = 0;

        long frameDuration = (long) (1000 / configuration.FPS);

        while (!exit && !testMode) {
            start = System.currentTimeMillis();
            dt = start - previous;
            if (sceneManager.getCurrent() != null) {
                input();
                update(dt);
                draw();
            }
            frames++;
            timeFrame += dt;
            if (timeFrame > 1000) {
                timeFrame = 0;
                realFPS = frames;
                frames = 0;
            }
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed > 0 && elapsed < frameDuration) {
                try {
                    Thread.sleep(frameDuration - (System.currentTimeMillis() - start));
                } catch (InterruptedException e) {
                    logger.error("The Game Thread has been interrupted");
                    Thread.currentThread().interrupt();
                }
            }
            previous = start;
        }
    }

    /**
     * Manage the input
     */
    private void input() {
        sceneManager.getCurrent().input();
    }

    /**
     * Update all the game mechanism
     */
    private void update(long dt) {
        sceneManager.getCurrent().update(dt);
    }

    /**
     * Draw the things from the game.
     */
    private void draw() {
        renderer.render();
        window.draw(realFPS, renderer.getBuffer());
    }

    /**
     * Free everything
     */
    private void dispose() {
        renderer.clear();
        if (!testMode) {
            window.close();
        }
    }

    /**
     * Request the game to exit.
     */
    public void requestExit() {
        this.exit = true;
    }

    public Window getWindow() {
        return window;
    }

    public Render getRender() {
        return renderer;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // nothing to do now
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // nothing to do now

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F11:
                renderer.saveScreenshot();
                break;
            case KeyEvent.VK_ESCAPE:
                this.exit = true;
                break;
            default:
                break;
        }

    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public static void main(String[] argc) {
        try {
            Game game = new Game("config");
            game.run(argc);
        } catch (Exception e) {
            logger.error("Unable to run the game", e);
        }
    }

}
