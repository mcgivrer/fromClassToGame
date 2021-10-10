// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.snapgames.fromclasstogame.core.config.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import fr.snapgames.fromclasstogame.core.Game;

public class CliManager {
    @SuppressWarnings("unused")
    private Game game;
    private Map<String, ArgParser<?>> argParsers = new HashMap<>();
    private Map<String, ArgParser<?>> configParsers = new HashMap<>();
    private Map<String, Object> values = new HashMap<>();

    public CliManager(Game g) {
        this.game = g;
    }

    public void add(ArgParser<?> ap) {
        argParsers.put(ap.getName(), ap);
        configParsers.put(ap.getConfigKey(), ap);
        System.out.print("add cli parser for " + ap.getDescription());
    }

    public void parse(String[] args) {
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

    private void parseArguments(String arg) {
        String[] itemValue = arg.split("=");
        for (ArgParser<?> ap : argParsers.values()) {
            if (ap.getShortKey().equals(itemValue[0]) || ap.getLongKey().equals(itemValue[0])) {
                if (ap.validate(itemValue[1])) {
                    values.put(ap.getName(), ap.getValue());
                } else {
                    System.err.print(ap.getErrorMessage(null));
                }
            }
        }
    }

    public Object getValue(String key) throws ArgumentUnknownException {
        if (values.containsKey(key)) {
            return values.get(key);
        } else {
            return argParsers.get(key).getDefaultValue();
        }
    }

    public boolean isExists(String key) {
        return values.containsKey(key);
    }

    public void parse(ResourceBundle configFile) {
        while (configFile.getKeys().hasMoreElements()) {
            String k = configFile.getKeys().nextElement();
            ArgParser<?> ap = configParsers.get(k);
            if(ap.validate(configFile.getString(k))){
                values.put(ap.getName(), ap.getValue());
            }else{
                System.err.print(String.format("The key %s is unknown",k));
            }
        }
    }
}