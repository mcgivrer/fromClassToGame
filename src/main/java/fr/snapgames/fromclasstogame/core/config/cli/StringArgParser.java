package fr.snapgames.fromclasstogame.core.config.cli;

import fr.snapgames.fromclasstogame.core.config.cli.AbstractArgParser;

import java.util.Optional;

public class StringArgParser extends AbstractArgParser<String> {

    public StringArgParser(String name, String shortKey, String longKey, String description, String configKey, String defaultValue) {
        super(name, description, shortKey, longKey, configKey);
        setDefaultValue(defaultValue);
    }

    @Override
    public boolean validate(String strValue) {
        Optional<String> valueMightBeNull = Optional.of(strValue);
        if (valueMightBeNull.isPresent()) {
            this.value = valueMightBeNull.get();
        } else {
            this.value = defaultValue;
        }
        return true;
    }

    @Override
    public String parse(String strValue) {
        return strValue;
    }
}
