package fr.snapgames.fromclasstogame.core.config;

import fr.snapgames.fromclasstogame.core.config.cli.*;
import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class Configuration {
    /**
     * path to the default configuration file
     */
    private static final String CFG_KEY_CONFIG = "config";

    /**
     * value of the default debug level (0 to 5 fixing the detailed granularity,
     * default must be set to 0 for normal mode)
     */
    private static final String CFG_KEY_DEBUG = "debug";

    /**
     * default title of the window.
     */
    private static final String CFG_KEY_TITLE = "title";

    /**
     * default scene code to be activated
     */
    private static final String CFG_KEY_SCENE = "scene";

    /**
     * list of scenes
     */
    private static final String CFG_KEY_SCENES = "scenes";

    /**
     * destination display to show the game window
     */
    private static final String CFG_KEY_DISPLAY = "display";


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
     * Audio mute parameter.
     */
    private static final String CFG_KEY_AUDIO_MUTE = "mute";

    /**
     * Audio Sound Volume
     */
    private static final String CFG_KEY_AUDIO_SOUND_VOLUME = "sound";

    /**
     * Audio Sound Volume
     */
    private static final String CFG_KEY_AUDIO_MUSIC_VOLUME = "music";


    /**
     * vector of the default gravty for the physic Engine
     */
    private static final String CFG_KEY_WORLD_GRAVITY = "gravity";
    /**
     * Deign the world width at start.
     */
    private static final String CFG_KEY_WORLD_WIDTH = "worldWidth";
    /**
     * Deign the world width at start.
     */
    private static final String CFG_KEY_WORLD_HEIGHT = "worldHeight";


    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    public ResourceBundle defaultValues;
    /**
     * Parameters cli configuration
     */
    public CliManager cm;

    public String title = "fromClassToGame";

    /**
     * Display parameters
     */
    public int width = 320;
    public int height = 200;
    public double scale = 1.0;
    public double FPS = 60;
    public int defaultScreen = 0;

    /**
     * world parameters
     */
    public int worldWidth;
    public int worldHeight;
    public Vector2d gravity = new Vector2d(0.0, -0.981);

    public String scenes = "";
    public String defaultScene = "";

    /**
     * Tilemap and Level root path
     */
    public String levelPath;


    /**
     * Audio Parameters
     */
    public boolean mute;
    public float soundVolume;
    public float musicVolume;

    /**
     * Debug level
     */
    public int debugLevel;
    /**
     * path to configuration file.
     */
    private String configPath;


    public Configuration(String configurationPath) {
        try {
            cm = new CliManager();
            this.configPath = configurationPath;
            initializeArgParser(configurationPath);
            logger.info("** > Configuration file '{}' loaded [@ {}]", configurationPath,
                    System.currentTimeMillis());
        } catch (Exception e) {
            logger.error("Unable to read configuration", e);
        }
    }

    private void initializeArgParser(String configurationPath) {

        /*---- Debug level mode activation ----*/
        cm.add(new IntegerArgParser(CFG_KEY_DEBUG, "dbg", CFG_KEY_DEBUG,
                "set the value for the debug mode (0 to 5)", "game.setup.debugLevel", 0));

        /*---- Game window title ----*/
        cm.add(new StringArgParser(CFG_KEY_TITLE, "t", CFG_KEY_TITLE, "Define the game window title",
                "game.setup.title", CFG_KEY_TITLE));

        /*---- Display screen setup ----*/
        cm.add(new IntegerArgParser(CFG_KEY_WIDTH, "w", CFG_KEY_WIDTH, "default width of the game screen",
                "game.setup.width", 320));
        cm.add(new IntegerArgParser(CFG_KEY_HEIGHT, "h", CFG_KEY_HEIGHT, "default height of the game screen",
                "game.setup.height", 200));
        cm.add(new DoubleArgParser(CFG_KEY_SCALE, "sc", CFG_KEY_SCALE, "default game screen scaling",
                "game.setup.scale", 2.0));
        cm.add(new IntegerArgParser(CFG_KEY_FPS, "f", "fps", "set the frame per second (25-60)",
                "game.setup.fps", 60));
        cm.add(new IntegerArgParser(CFG_KEY_DISPLAY, "di", CFG_KEY_DISPLAY,
                "set the default display to play the game", "game.setup.screen", 0));

        /*---- Scene setup ----*/
        cm.add(new StringArgParser(CFG_KEY_SCENES, "ss", CFG_KEY_SCENES,
                "Define the scene names and classes to initialize the game", "game.setup.scenes.list",
                ""));
        cm.add(new StringArgParser(CFG_KEY_SCENE, "sd", CFG_KEY_SCENE, "Define the default scene to start with",
                "game.setup.scenes.default", ""));
        cm.add(new StringArgParser(CFG_KEY_CONFIG, "c", CFG_KEY_CONFIG,
                "set the path and file to be loaded for configuration", "game.setup.config.filename",
                configurationPath));

        /*---- Parameters for Audio service ----*/
        cm.add(new BooleanArgParser(CFG_KEY_AUDIO_MUTE, "mute", CFG_KEY_AUDIO_MUTE,
                "Define the audio mute flag", "game.audio.mote.flag", false));
        cm.add(new FloatArgParser(CFG_KEY_AUDIO_SOUND_VOLUME, "snd", CFG_KEY_AUDIO_SOUND_VOLUME,
                "Define the audio volume level (0.0 to 1.0)", "game.audio.volume.sound", 1.0f));
        cm.add(new FloatArgParser(CFG_KEY_AUDIO_MUSIC_VOLUME, "mus", CFG_KEY_AUDIO_SOUND_VOLUME,
                "Define the music volume level (0.0 to 1.0)",
                "game.audio.volume.music", 0.8f));

        /*---- Parameters for World setup ----*/
        cm.add(new Vector2dArgParser(CFG_KEY_WORLD_GRAVITY, "g", CFG_KEY_WORLD_GRAVITY, "define the default game gravity",
                "game.setup.world.gravity", new Vector2d(0, -0.981)));
        cm.add(new IntegerArgParser(CFG_KEY_WORLD_WIDTH, "ww", CFG_KEY_WORLD_WIDTH,
                "Define the world width (in pixel, max 2000)",
                "game.setup.world.width", 800));
        cm.add(new IntegerArgParser(CFG_KEY_WORLD_HEIGHT, "ww", CFG_KEY_WORLD_HEIGHT,
                "Define the world heiht (in pixel, max 2000)",
                "game.setup.world.height", 800));
    }

    /**
     * Parse configuration file key by key and set the corresponding values in the
     * configuration attributes.
     */
    public void readValuesFromFile() {

        ResourceBundle.clearCache(this.getClass().getClassLoader());
        ResourceBundle config = ResourceBundle.getBundle(this.configPath, Locale.ROOT,
                this.getClass().getClassLoader());
        this.defaultValues = config;
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
        this.configPath = (String) cm.getValue(CFG_KEY_CONFIG);
        this.soundVolume = (Float) cm.getValue(CFG_KEY_AUDIO_SOUND_VOLUME);
        this.musicVolume = (Float) cm.getValue(CFG_KEY_AUDIO_MUSIC_VOLUME);

        this.worldWidth = (Integer) cm.getValue(CFG_KEY_WORLD_WIDTH);
        this.worldHeight = (Integer) cm.getValue(CFG_KEY_WORLD_HEIGHT);
        this.gravity = (Vector2d) cm.getValue(CFG_KEY_WORLD_GRAVITY);

    }

    public Configuration parseArgs(String[] argv) throws ArgumentUnknownException {
        // parse argument a first time to detect a different config file is provided
        cm.parseArguments(argv);
        getValuesFromCM();
        // read default values from config files
        readValuesFromFile();
        // reparse args to override the mandatory attributes
        cm.parseArguments(argv);
        // retrieve the final values and set the useful config.
        getValuesFromCM();
        return this;
    }
}
