package fr.snapgames.fromclasstogame.core.config;

import fr.snapgames.fromclasstogame.core.config.cli.AbstractArgParser;

public class StringArgParser extends AbstractArgParser<String> {

    public StringArgParser(String name, String description, String shortKey, String longKey, String configKey,String defaultValue) {
        super(name, description, shortKey, longKey, configKey);
        setDefaultValue(defaultValue);
    }

    @Override
    public void parseFromConfigFile(String line) {
        // TODO Auto-generated method stub

    }

    @Override
    public String parse(String strValue) {
        return strValue;
    }

    @Override
    public boolean validate(String strValue) {
        return true;
    }

}
