// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.snapgames.fromclasstogame.core.config.cli;

public interface ArgParser<T> {
    public boolean validate(String strValue);

    public T getValue();

    public String getShortKey();

    public String getLongKey();

    public String getName();

    public String getDescription();

    public String getErrorMessage(Object[] args);

    public T getDefaultValue();

    public String getConfigKey();
}