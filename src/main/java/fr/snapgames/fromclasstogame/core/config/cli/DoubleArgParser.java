// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.snapgames.fromclasstogame.core.config.cli;

public class DoubleArgParser extends AbstractArgParser<Double> {

    public DoubleArgParser(String name, String shortKey, String longKey, String description, String configKey,
                           Double defaultValue) {
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
    public Double parse(String strValue) {
        Double i = Double.parseDouble(strValue);
        return i;
    }
}
