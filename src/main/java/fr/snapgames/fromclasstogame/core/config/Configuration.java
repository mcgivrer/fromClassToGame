package fr.snapgames.fromclasstogame.core.config;

import java.util.ResourceBundle;

import fr.snapgames.fromclasstogame.core.config.cli.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.config.cli.CliManager;
import fr.snapgames.fromclasstogame.core.config.cli.DoubleArgParser;
import fr.snapgames.fromclasstogame.core.config.cli.IntegerArgParser;
import fr.snapgames.fromclasstogame.core.exceptions.cli.UnknownArgumentException;

public class Configuration {

    public ResourceBundle defaultConfig;
    public CliManager cm;

    public String title = "fromClassToGame";

    public int defaultScreen;
    public int width = 320;
    public int height = 200;
    public double scale = 1.0;

    public Double gravity = 0.0;

    public double FPS = 60;

    public String scenes = "";
    public String defaultScene = "";

    public int debugLevel;

    public Configuration(String configurationPath) {
        cm = new CliManager(null);
        defaultConfig = ResourceBundle.getBundle(configurationPath);
        initializeArgParser();
        readValuesFromFile();

    }

    private void initializeArgParser() {

        cm.add(new IntegerArgParser("debug", "dbg", "debug", "set the value for the debug mode (0 to 5)",
                "game.setup.debugLevel", 0));
        cm.add(new StringArgParser("title", "t", "title", "Define the game window title", "game.setup.title", "title"));
        cm.add(new IntegerArgParser("width", "w", "width", "default width of the game screen", "game.setup.width",
                320));
        cm.add(new IntegerArgParser("height", "h", "height", "default height of the game screen", "game.setup.height",
                200));
        cm.add(new DoubleArgParser("scale", "sc", "scale", "default game screen scaling", "game.setup.scale", 2.0));
        cm.add(new IntegerArgParser("FPS", "f", "fps", "set the frame per second (25-60)", "game.setup.fps", 60));
        cm.add(new DoubleArgParser("gravity", "g", "gravity", "define the default game gravity", "game.setup.gravity",
                0.981));
        cm.add(new IntegerArgParser("display", "disp", "display", "set the default display to play the game",
                "game.setup.screen", 0));
        cm.add(new StringArgParser("scenes", "scns", "scenes",
                "Define the scene names and classes to initalize the game", "game.setup.scenes", ""));
        cm.add(new StringArgParser("scene", "scn", "scene", "Define the defaul scene to start with",
                "game.setup.scene.default", ""));

    }

    public void readValuesFromFile() {
        try {

            this.width = (Integer) cm.getValue("width");
            this.height = (Integer) cm.getValue("height");
            this.scale = (Double) cm.getValue("scale");
            this.debugLevel = (Integer) cm.getValue("debug");
            this.FPS = (Integer) cm.getValue("FPS");
            this.defaultScreen = (Integer) cm.getValue("display");
            this.defaultScene = (String) cm.getValue("scene");
            this.title = (String) cm.getValue("title");

        } catch (ArgumentUnknownException e) {
            System.err.println("unable to parse configuration : " + e.getMessage());
        }
    }

    public Configuration parseArgs(String[] argv) throws UnknownArgumentException {
        cm.parse(argv);
        return this;
    }
}
