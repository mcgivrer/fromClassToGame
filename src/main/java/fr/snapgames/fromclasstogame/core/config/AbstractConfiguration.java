package fr.snapgames.fromclasstogame.core.config;

import fr.snapgames.fromclasstogame.core.config.cli.*;
import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class AbstractConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AbstractConfiguration.class);

    public ResourceBundle defaultValues;
    public CliManager cm;
    String configPath = "config.properties";

    public AbstractConfiguration(String configurationPath) {
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

    public abstract void initializeArgParser(String configurationPath);

    /**
     * Parse configuration file key by key and set the corresponding values in the
     * configuration attributes.
     */
    public void readValuesFromFile() {

        try {
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
        } catch (Exception e) {
            logger.error("unable to read configuration from {}", this.configPath);
        }
    }

    /**
     * After parsing the CLI, retrieve the extracted values and set the
     * Configuration attributes.
     */
    public abstract void getValuesFromCM();

    public AbstractConfiguration parseArgs(String[] argv) throws ArgumentUnknownException {
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
