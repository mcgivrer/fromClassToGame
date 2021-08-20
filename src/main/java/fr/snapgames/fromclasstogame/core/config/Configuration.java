package fr.snapgames.fromclasstogame.core.config;

import java.util.ResourceBundle;

import fr.snapgames.fromclasstogame.core.exceptions.cli.UnknownArgumentException;

public class Configuration {

    private ResourceBundle defaultConfig;

    public String title = "fromClassToGame";

    public int width = 320;
    public int height = 200;
    public double scale = 1.0;

    public double FPS = 60;

    public String scenes = "";
    public String defaultScene = "";

    public int debugLevel;

    public Configuration(String configurationPath) {

        defaultConfig = ResourceBundle.getBundle(configurationPath);
        readValuesFromFile();

    }

    public void readValuesFromFile() {

        this.debugLevel = Integer.parseInt(defaultConfig.getString("game.setup.debugLevel"));
        this.width = Integer.parseInt(defaultConfig.getString("game.setup.width"));
        this.height = Integer.parseInt(defaultConfig.getString("game.setup.height"));
        this.scale = Double.parseDouble(defaultConfig.getString("game.setup.scale"));
        this.FPS = Double.parseDouble(defaultConfig.getString("game.setup.fps"));
        this.title = defaultConfig.getString("game.setup.title");
        this.scenes = defaultConfig.getString("game.setup.scenes");
        this.defaultScene = defaultConfig.getString("game.setup.scene.default");
    }

    public Configuration parseArgs(String[] argv) throws UnknownArgumentException {
        if (argv != null) {
            for (String arg : argv) {
                String[] values = arg.split("=");
                switch (values[0].toLowerCase()) {
                    case "debug":
                        this.debugLevel = Integer.parseInt(values[1]);
                        break;
                    case "width":
                        this.width = Integer.parseInt(values[1]);
                        break;
                    case "height":
                        this.height = Integer.parseInt(values[1]);
                        break;
                    case "scale":
                        this.scale = Double.parseDouble(values[1]);
                        break;
                    case "fps":
                        this.FPS = Double.parseDouble(values[1]);
                        break;
                    case "title":
                        this.title = values[1];
                        break;
                    case "scene":
                        this.defaultScene = values[1];
                        break;
                    default:
                        throw new UnknownArgumentException(String.format("The argument %s is unknown", arg));
                }
            }
        }
        return this;
    }
}
