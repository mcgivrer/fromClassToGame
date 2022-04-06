package fr.snapgames.fromclasstogame.devtools;

import fr.snapgames.fromclasstogame.core.config.cli.AbstractArgParser;

import java.awt.*;
import java.util.Optional;

public class ColorArgParser extends AbstractArgParser<Color> {

    public ColorArgParser(String name, String description, String shortKey, String longKey, String configKey,
                          Color defaultValue) {
        super(name, description, shortKey, longKey, configKey);
        setDefaultValue(defaultValue);
    }

    @Override
    public boolean validate(String strValue) {
        Optional<String> valueMightBeNull = Optional.of(strValue);
        if (valueMightBeNull.isPresent()) {
            this.value = Color.decode(valueMightBeNull.get());
        } else {
            this.value = defaultValue;
        }
        return true;
    }

    @Override
    public Color parse(String strValue) {
        return this.value;
    }
}
