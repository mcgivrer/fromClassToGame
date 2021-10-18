// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.snapgames.fromclasstogame.core.config.cli;

import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class Vector2dArgParser extends AbstractArgParser<Vector2d> {

    public Vector2dArgParser(String name, String shortKey, String longKey, String description, String configKey,
                             Vector2d defaultValue) {
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
    public Vector2d parse(String strValue) {
        Vector2d v = Vector2d.ZERO;
        if (strValue.startsWith("v2d(")) {
            strValue = strValue.substring(4, strValue.length() - 1);
            String vals[] = strValue.split(",");
            Double vX = Double.parseDouble(vals[0]);
            Double vY = Double.parseDouble(vals[1]);
            v = new Vector2d(vX, vY);
        } else {
            System.err.println("name=" + name + ", strValue=" + strValue + " => " + v);
        }
        return v;
    }
}
