// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.snapgames.fromclasstogame.core.config.cli;

public class IntegerArgParser extends AbstractArgParser<Integer> {

    public IntegerArgParser() {
        super();
    }

    public IntegerArgParser(String name, String shortKey, String longKey, String description, String configKey, int defaultValue) {
        super(name, shortKey, longKey, description, configKey);
        setDefaultValue(defaultValue);
    }

    @Override
    public boolean validate(String strValue) {
        value = defaultValue;
        try {
            value = parse(strValue);
        } catch (Exception e) {
            value = defaultValue;
            errorMessage += String.format("value %s for argument %s is not possible.reset to default Value %s",
                    strValue, name, defaultValue);
            return false;
        }
        return true;
    }

    @Override
    public Integer parse(String strValue) {
        int value = Integer.parseInt(strValue);
        return value;
    }
}