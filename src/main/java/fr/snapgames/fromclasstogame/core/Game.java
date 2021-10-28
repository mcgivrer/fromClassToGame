package fr.snapgames.fromclasstogame.core;

import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.gfx.Window;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.PhysicEngine;
import fr.snapgames.fromclasstogame.core.physic.World;
import fr.snapgames.fromclasstogame.core.physic.collision.CollisionSystem;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;

/**
 * Project: From Class To Game
 * <p>
 * A First class to build a game attempt.
 *
 * @author Frédéric Delorme
 * @since 0.0.1
 */
public class Game implements ActionHandler.ActionListener {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    public boolean exit = false;
    public boolean testMode = false;
    private long realFPS = 60;
    private Window window;
    private Render renderer;
    private ActionHandler actionHandler;
    private SceneManager sceneManager;
    private Configuration configuration;
    private CollisionSystem cs;
    private PhysicEngine pe;

    /**
     * the mandatory default constructor
     */
    public Game() {
        this("config");
    }

    /**
     * A constructor mainly used for test purpose.
     *
     * @param configPath path to the configuration properties file
     */
    public Game(String configPath) {
        logger.info("** > Create Game [@ {}]", System.currentTimeMillis());
        configuration = new Configuration(configPath);
    }

    /**
     * Initialize a game with some attributes.
     *
     * @param t title for the game window
     * @param w width of the game window
     * @param h height of the game window
     */
    public Game(String t, int w, int h) {
        this("config");
        configuration.title = t;
        configuration.width = w;
        configuration.height = h;
    }

    public static void main(String[] argc) {
        try {
            Game game = new Game("config");
            game.run(argc);
        } catch (Exception e) {
            logger.error("Unable to run the game", e);
        }
    }

    /**
     * Initialization of the display window and everything the game will need.
     */
    public void initialize(String[] argv) throws ArgumentUnknownException {
        SystemManager.initialize(this);
        configuration.parseArgs(argv);

        SystemManager.add(Render.class);
        SystemManager.add(PhysicEngine.class);
        SystemManager.add(ActionHandler.class);
        SystemManager.add(SceneManager.class);
        SystemManager.add(CollisionSystem.class);

        SystemManager.configure(configuration);

        renderer = (Render) SystemManager.get(Render.class);
        renderer.setDebugLevel(configuration.debugLevel);

        window = new Window(configuration.title, (int) (configuration.width * configuration.scale),
                (int) (configuration.height * configuration.scale));

        pe = (PhysicEngine) SystemManager.get(PhysicEngine.class);
        cs = (CollisionSystem) SystemManager.get(CollisionSystem.class);

        actionHandler = (ActionHandler) SystemManager.get(ActionHandler.class);
        actionHandler.setWindow(window);
        actionHandler.add(this);

        sceneManager = (SceneManager) SystemManager.get(SceneManager.class);
        logger.info("** > Game initialized at {}", System.currentTimeMillis());

    }

    /**
     * Entrypoint for the game. can parse the argc from the java command line.
     *
     * @throws ArgumentUnknownException
     */
    public void run(String argv[]) throws ArgumentUnknownException {
        logger.info("** > Start Game run execution at [@ {}]", System.currentTimeMillis());
        initialize(argv);
        createScene();
        loop();
        if (!testMode) {
            dispose();
        }
        logger.info("** > End Game run execution at [@ {}]", System.currentTimeMillis());
    }

    private void createScene() {
        sceneManager.activate();
        actionHandler.add(sceneManager.getCurrent());
    }

    /**
     * the famous main game loop where everything happened.
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
        sceneManager.getCurrent().input(actionHandler);
    }

    /**
     * Update all the game mechanism
     */
    public void update(long dt) {

        if (pe != null) {
            pe.update(dt);
        }
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
        if (!testMode) {
            window.close();
        }
        SystemManager.dispose();
        logger.info("** > Game disposed all dependencies [@ {}]", System.currentTimeMillis());

    }

    /**
     * Request the game to exit.
     */
    public void requestExit() {
        this.exit = true;
        logger.info("** > User Request to quit the game");
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
            case KeyEvent.VK_F3:
                renderer.requestScreenShot();
                break;
            case KeyEvent.VK_F11:
                window.switchFullScreen();
                break;
            case KeyEvent.VK_ESCAPE:
                this.exit = true;
                break;
            default:
                break;
        }

    }

    public Game setWorld(World world) {
        this.pe.setWorld(world);
        this.renderer.setWorld(world);
        return this;
    }

    public SceneManager getSceneManager() {
        return this.sceneManager;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public PhysicEngine getPhysicEngine() {
        return (PhysicEngine) SystemManager.get(PhysicEngine.class);
    }

    public Window getWindow() {
        return window;
    }

    public Render getRender() {
        return (Render) SystemManager.get(Render.class);
    }

    @Override
    public void onAction(ActionHandler.ACTIONS action) {
        getSceneManager().getCurrent().onAction(action);
    }
}
