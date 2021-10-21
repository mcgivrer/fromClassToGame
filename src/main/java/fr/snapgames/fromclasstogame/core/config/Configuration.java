package fr.snapgames.fromclasstogame.core.config;

import fr.snapgames.fromclasstogame.core.config.cli.*;
import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

    public ResourceBundle defaultConfig;
    public CliManager cm;

    public String title = "fromClassToGame";

    public int defaultScreen;
    public int width = 320;
    public int height = 200;
    public double scale = 1.0;

    public Vector2d gravity = new Vector2d(0.0, 0.0);

    public double FPS = 60;

    public String scenes = "";
    public String defaultScene = "";

    public int debugLevel;

    public Configuration(String configurationPath) {

        cm = new CliManager();
        defaultConfig = ResourceBundle.getBundle(configurationPath);
        initializeArgParser();
        readValuesFromFile();
        logger.info("** > Configuration file '{}' loaded [@ {}]", configurationPath, System.currentTimeMillis());
    }

    private void initializeArgParser() {

        cm.add(new IntegerArgParser("debug",
                "dbg",
                "debug",
                "set the value for the debug mode (0 to 5)",
                "game.setup.debugLevel",
                0));
        cm.add(new StringArgParser("title",
                "t",
                "title",
                "Define the game window title",
                "game.setup.title",
                "title"));
        cm.add(new IntegerArgParser("width",
                "w",
                "width",
                "default width of the game screen",
                "game.setup.width",
                320));
        cm.add(new IntegerArgParser("height",
                "h",
                "height",
                "default height of the game screen",
                "game.setup.height",
                200));
        cm.add(new DoubleArgParser("scale",
                "sc",
                "scale",
                "default game screen scaling",
                "game.setup.scale",
                2.0));
        cm.add(new IntegerArgParser("FPS",
                "f",
                "fps",
                "set the frame per second (25-60)",
                "game.setup.fps",
                60));
        cm.add(new Vector2dArgParser("gravity",
                "g",
                "gravity",
                "define the default game gravity",
                "game.setup.world.gravity",
                new Vector2d(0, -0.981)));
        cm.add(new IntegerArgParser("display",
                "di",
                "display",
                "set the default display to play the game",
                "game.setup.screen",
                0));
        cm.add(new StringArgParser("scenes",
                "ss",
                "scenes",
                "Define the scene names and classes to initialize the game",
                "game.setup.scenes",
                ""));
        cm.add(new StringArgParser("scene",
                "sd",
                "scene",
                "Define the default scene to start with",
                "game.setup.scene.default",
                ""));
    }

    public void readValuesFromFile() {
        try {
            cm.parse(defaultConfig);
            getValuesFromCM();
        } catch (ArgumentUnknownException e) {
            logger.error("unable to parse configuration", e);
        }
    }

    private void getValuesFromCM() throws ArgumentUnknownException {
        this.debugLevel = (Integer) cm.getValue("debug");
        this.title = (String) cm.getValue("title");
        this.width = (Integer) cm.getValue("width");
        this.height = (Integer) cm.getValue("height");
        this.scale = (Double) cm.getValue("scale");
        this.defaultScreen = (Integer) cm.getValue("display");
        this.FPS = (Integer) cm.getValue("FPS");
        this.defaultScene = (String) cm.getValue("scene");
        this.scenes = (String) cm.getValue("scenes");
        this.gravity = (Vector2d) cm.getValue("gravity");
    }

    public Configuration parseArgs(String[] argv) throws ArgumentUnknownException {
        cm.parse(argv);
        getValuesFromCM();
        return this;
    }
}
