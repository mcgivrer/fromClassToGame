// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.snapgames.fromclasstogame.core.config.cli;

public class FloatArgParser extends AbstractArgParser<Float> {

    public FloatArgParser(String name, String shortKey, String longKey, String description, String configKey,
                          Float defaultValue) {
        super(name, description, shortKey, longKey, configKey);
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
    public Float parse(String strValue) {
        Float i = Float.parseFloat(strValue);
        return i;
    }
}
