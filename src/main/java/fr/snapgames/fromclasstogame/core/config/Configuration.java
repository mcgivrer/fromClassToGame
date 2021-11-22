package fr.snapgames.fromclasstogame.core.config;

import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.snapgames.fromclasstogame.core.config.cli.CliManager;
import fr.snapgames.fromclasstogame.core.config.cli.DoubleArgParser;
import fr.snapgames.fromclasstogame.core.config.cli.IntegerArgParser;
import fr.snapgames.fromclasstogame.core.config.cli.StringArgParser;
import fr.snapgames.fromclasstogame.core.config.cli.Vector2dArgParser;
import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class Configuration {
<<<<<<< HEAD
        
        private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

        public ResourceBundle defaultValues = ResourceBundle.getBundle("config",Locale.ROOT);

        public CliManager cm;
        public String levelPath;
        public String title = "fromClassToGame";
=======
    /**
     * path to the default configuration file
     */
    private static final String CFG_KEY_CONFIG = "config";

    /**
     * default scene code to be activated
     */
    private static final String CFG_KEY_SCENE = "scene";
>>>>>>> a29d340ad9fb966abeeb35e957958430525a0fac

    /**
     * list of scenes
     */
    private static final String CFG_KEY_SCENES = "scenes";

    /**
     * destination display to show the game window
     */
    private static final String CFG_KEY_DISPLAY = "display";

    /**
     * vector of the default gravty for the physic Engine
     */
    private static final String CFG_KEY_GRAVITY = "gravity";

    /**
     * Frame Per Second to set display framerate.
     */
    private static final String CFG_KEY_FPS = "FPS";

    /**
     * pixel scale of the game rendering
     */
    private static final String CFG_KEY_SCALE = "scale";

    /**
     * default height of the window.
     */
    private static final String CFG_KEY_HEIGHT = "height";

    /**
     * default width of the window.
     */
    private static final String CFG_KEY_WIDTH = "width";

    /**
     * default title of the window.
     */
    private static final String CFG_KEY_TITLE = "title";

    /**
     * value of the default debug level (0 to 5 fixing the details granularity,
     * default must be set to 0 for normal mode)
     */
    private static final String CFG_KEY_DEBUG = "debug";

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

    public ResourceBundle defaultValues;
    public CliManager cm;
    public String levelPath;
    public String title = "fromClassToGame";

    public int width = 320;
    public int height = 200;
    public double scale = 1.0;

    public Vector2d gravity = new Vector2d(0.0, -0.981);
    public double FPS = 60;
    public String scenes = "";
    public String defaultScene = "";
    public int defaultScreen = 0;

    public int debugLevel;
    private String configPath;

    public Configuration(String configurationPath) {
        try {
            cm = new CliManager();
            initializeArgParser(configurationPath);
            logger.info("** > Configuration file '{}' loaded [@ {}]", configurationPath,
                    System.currentTimeMillis());
        } catch (Exception e) {
            logger.error("Unable to read configuration", e);
        }
    }

    private void initializeArgParser(String configurationPath) {

        cm.add(new IntegerArgParser(CFG_KEY_DEBUG, "dbg", CFG_KEY_DEBUG,
                "set the value for the debug mode (0 to 5)", "game.setup.debugLevel", 0));
        cm.add(new StringArgParser(CFG_KEY_TITLE, "t", CFG_KEY_TITLE, "Define the game window title",
                "game.setup.title", CFG_KEY_TITLE));
        cm.add(new IntegerArgParser(CFG_KEY_WIDTH, "w", CFG_KEY_WIDTH, "default width of the game screen",
                "game.setup.width", 320));
        cm.add(new IntegerArgParser(CFG_KEY_HEIGHT, "h", CFG_KEY_HEIGHT, "default height of the game screen",
                "game.setup.height", 200));
        cm.add(new DoubleArgParser(CFG_KEY_SCALE, "sc", CFG_KEY_SCALE, "default game screen scaling",
                "game.setup.scale", 2.0));
        cm.add(new IntegerArgParser(CFG_KEY_FPS, "f", "fps", "set the frame per second (25-60)",
                "game.setup.fps", 60));
        cm.add(new Vector2dArgParser(CFG_KEY_GRAVITY, "g", CFG_KEY_GRAVITY, "define the default game gravity",
                "game.setup.world.gravity", new Vector2d(0, -0.981)));
        cm.add(new IntegerArgParser(CFG_KEY_DISPLAY, "di", CFG_KEY_DISPLAY,
                "set the default display to play the game", "game.setup.screen", 0));
        cm.add(new StringArgParser(CFG_KEY_SCENES, "ss", CFG_KEY_SCENES,
                "Define the scene names and classes to initialize the game", "game.setup.scenes.list",
                ""));
        cm.add(new StringArgParser(CFG_KEY_SCENE, "sd", CFG_KEY_SCENE, "Define the default scene to start with",
                "game.setup.scenes.default", ""));
        cm.add(new StringArgParser(CFG_KEY_CONFIG, "c", CFG_KEY_CONFIG,
                "set the path and file to be loaded for configuration", "game.setup.config.filename",
                configurationPath));
    }

    /**
     * Parse configuration file key by key and set the corresponding values in the
     * configuration attributes.
     */
    public void readValuesFromFile() {

        ResourceBundle.clearCache(this.getClass().getClassLoader());
        ResourceBundle config = ResourceBundle.getBundle(this.configPath, Locale.ENGLISH,
                this.getClass().getClassLoader());
        if (config != null) {
            cm.parseConfigFile(config);
            getValuesFromCM();
        } else {
            logger.error("unable to set configuration from {}", this.configPath);
        }
    }

    /**
     * After parsing the CLI, retrieve the extracted values and set the
     * Configuration attributes.
     */
    private void getValuesFromCM() {
        this.debugLevel = (Integer) cm.getValue(CFG_KEY_DEBUG);
        this.title = (String) cm.getValue(CFG_KEY_TITLE);
        this.width = (Integer) cm.getValue(CFG_KEY_WIDTH);
        this.height = (Integer) cm.getValue(CFG_KEY_HEIGHT);
        this.scale = (Double) cm.getValue(CFG_KEY_SCALE);
        this.defaultScreen = (Integer) cm.getValue(CFG_KEY_DISPLAY);
        this.FPS = (Integer) cm.getValue(CFG_KEY_FPS);
        this.defaultScene = (String) cm.getValue(CFG_KEY_SCENE);
        this.scenes = (String) cm.getValue(CFG_KEY_SCENES);
        this.gravity = (Vector2d) cm.getValue(CFG_KEY_GRAVITY);
        this.configPath = (String) cm.getValue(CFG_KEY_CONFIG);
    }

    public Configuration parseArgs(String[] argv) throws ArgumentUnknownException {
        cm.parseArguments(argv);
        getValuesFromCM();

        readValuesFromFile();

        getValuesFromCM();
        return this;
    }
}
