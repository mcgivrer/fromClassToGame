// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.snapgames.fromclasstogame.core.config.cli;

import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CliManager {
    private static final Logger logger = LoggerFactory.getLogger(CliManager.class);

    private Map<String, ArgParser<?>> argParsers = new HashMap<>();
    private Map<String, ArgParser<?>> configParsers = new HashMap<>();
    private Map<String, Object> values = new HashMap<>();

    public CliManager() {
    }

    public void add(ArgParser<?> ap) {
        argParsers.put(ap.getName(), ap);
        configParsers.put(ap.getConfigKey(), ap);
        logger.debug("add cli parser for '{}'", ap.getDescription());
    }

    public void parseArguments(String[] args) throws ArgumentUnknownException {
        if (args != null) {
            for (String arg : args) {
                if (arg.equals("h") || arg.equals("help")) {
                    System.out.println("\n\nCommand Usage:\n--------------");
                    for (ArgParser<?> ap : argParsers.values()) {
                        System.out.println("- " + ap.getDescription());
                    }
                    System.exit(0);
                } else {
                    parseArguments(arg);
                }
            }
        }
    }

    private void parseArguments(String arg) throws ArgumentUnknownException {
        String[] itemValue = arg.split("=");
        boolean argUnknown = true;
        for (ArgParser<?> ap : argParsers.values()) {
            if (ap.getShortKey().equals(itemValue[0]) || ap.getLongKey().equals(itemValue[0])) {
                if (ap.validate(itemValue[1])) {
                    values.put(ap.getName(), ap.getValue());
                    argUnknown = false;
                    logger.debug("- {}", ap.getDescription());
                } else {
                    logger.error(ap.getErrorMessage(null));
                }
            }
        }
        if (argUnknown) {
            throw new ArgumentUnknownException("Argument %s unknown", arg);
        }
    }

    public Object getValue(String key){
        if (values.containsKey(key)) {
            return values.get(key);
        } else {
            return argParsers.get(key).getDefaultValue();
        }
    }

    public boolean isExist(String key) {
        return values.containsKey(key);
    }

    public void parseConfigFile(ResourceBundle configFile) {
        Enumeration<String> configItems = configFile.getKeys();
        while (configItems.hasMoreElements()) {
            String k = configItems.nextElement();
            ArgParser<?> ap = configParsers.get(k);
            String configItemValue = configFile.getString(k);
            if (ap.validate(configItemValue)) {
                String name = ap.getName();
                Object value = ap.getValue();
                values.put(name, value);
                logger.debug("read parameter from file :" + ap.getName() + "=" + ap.getValue() + " (" + ap.getDescription() + ")");
            } else {
                logger.error("The key {} is unknown", k);
            }
        }
    }
}