package fr.snapgames.fromclasstogame.core;

import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.entity.EntityPoolManager;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.gfx.Window;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
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

    /**
     * Internal logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    /**
     * The <code>exit</code>  flag.
     */
    public static boolean exit = false;
    /**
     * testMode flag for unit test only.
     */
    public boolean testMode = false;
    /**
     * The default Frame per seconds rate for rendering purpose.
     */
    private long realFPS = 60;
    /**
     * The Window where all fun things happened.
     */
    private Window window;
    /**
     * THe rendering system, drawing beautiful GameObject onto the Window.
     */
    private Renderer renderer;
    /**
     * The action handler to
     */
    private ActionHandler actionHandler;
    /**
     * The Pool manager
     */
    private EntityPoolManager epm;
    /**
     * The Scene manager to switch gracely between game situations
     */
    private SceneManager sceneManager;
    /**
     * The unforgettable Configuration to dispatch needed default values to all the systems and game.
     */
    private Configuration configuration;
    /**
     * The bong bong system to detect when some GameObject bongs another one.
     */
    private CollisionSystem cs;
    /**
     * Finally the Physic Engine that computes all the fancy effects the Game is able to.
     */
    private PhysicEngine pe;

    /**
     * Hey, any player need a pause, this is the flag telling theh Game it's time for a coffee/tea/anything.
     */
    private boolean pause = false;

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

    /**
     * The unavoidable main java method entry point to start the magic.
     *
     * @param argc the command line list of arguments to override the <code>Configuration</code>.
     */
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
        /*
         * Why not initializing a bunch of systems to start this funky piece of game ?
         */
        SystemManager.add(Renderer.class);
        SystemManager.add(PhysicEngine.class);
        SystemManager.add(ActionHandler.class);
        SystemManager.add(SceneManager.class);
        SystemManager.add(CollisionSystem.class);
        SystemManager.add(EntityPoolManager.class);

        /*
         * And then configure ll those strange piece of code.
         */
        SystemManager.configure(configuration);

        ResourceManager.initialize(this);

        renderer = (Renderer) SystemManager.get(Renderer.class);
        renderer.setDebugLevel(configuration.debugLevel);

        window = new Window(configuration);

        pe = (PhysicEngine) SystemManager.get(PhysicEngine.class);
        cs = (CollisionSystem) SystemManager.get(CollisionSystem.class);

        actionHandler = (ActionHandler) SystemManager.get(ActionHandler.class);
        actionHandler.setWindow(window);
        actionHandler.add(this);

        sceneManager = (SceneManager) SystemManager.get(SceneManager.class);
        logger.info("** > Game initialized at {}", System.currentTimeMillis());

        epm = (EntityPoolManager) SystemManager.get(EntityPoolManager.class);
        epm.createPool(GameObject.class.getName());

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

    /**
     * Create the and active the default scene
     */
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
        long timeFrame = dt;
        long totalTmeFrame = 0;
        long gameTime = 0;

        long frameDuration = (long) (1000 / configuration.FPS);

        while (!exit && !testMode) {
            start = System.currentTimeMillis();
            dt = start - previous;
            if (sceneManager.getCurrent() != null) {
                input();
                update(dt);
                if (timeFrame < 1000 / configuration.FPS) {
                    draw();
                }
            }
            frames++;
            totalTmeFrame += dt;

            if (timeFrame > 1000 / configuration.FPS) {
                timeFrame = 0;
                realFPS = frames;
            }

            if (totalTmeFrame > 1000) {
                gameTime += totalTmeFrame;
                logger.debug("one more second: {} ms => {} ms", totalTmeFrame, gameTime);
                totalTmeFrame = 0;
                frames = 0;
            }
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed > 0 && elapsed < frameDuration) {
                try {
                    long waitTime = frameDuration - (System.currentTimeMillis() - start);
                    waitTime = waitTime > 0 ? waitTime : 1;
                    Thread.sleep(waitTime);
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
        sceneManager.input(actionHandler);
    }

    /**
     * Update all the game mechanism
     */
    public void update(long dt) {

        if (pe != null) {
            pe.update(dt);
        }
        if (cs != null) {
            cs.update(dt);
        }
        sceneManager.update(dt);
    }

    /**
     * Draw the things from the game.
     */
    private void draw() {
        renderer.draw();
        sceneManager.draw(renderer);
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
        ResourceManager.dispose();
        logger.info("** > Game disposed all dependencies [@ {}]", System.currentTimeMillis());

    }

    /**
     * Request the game to exit.
     */
    public void requestExit() {
        exit = true;
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
                if (actionHandler.getCtrl()) {
                    window.switchScreen();
                } else {
                    window.switchFullScreen();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                this.exit = true;
                break;
            case KeyEvent.VK_P:
            case KeyEvent.VK_PAUSE:
                pause = !pause;
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


    @Override
    public void onAction(Integer action) {
        getSceneManager().onAction(action);
    }

    public SceneManager getSceneManager() {
        return (SceneManager) SystemManager.get(SceneManager.class);
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

    public Renderer getRenderer() {
        return (Renderer) SystemManager.get(Renderer.class);
    }

    public boolean isPause() {
        return this.pause;
    }

    public void setPause(boolean p) {
        this.pause = p;
    }

    public EntityPoolManager getEPM() {
        return this.epm;
    }

    public CollisionSystem getCollisionSystem() {
        return cs;
    }
}
