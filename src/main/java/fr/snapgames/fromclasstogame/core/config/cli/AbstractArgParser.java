// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.snapgames.fromclasstogame.core.config.cli;

public abstract class AbstractArgParser<T> implements ArgParser<T> {

    protected String name;
    protected String shortKey;
    protected String longKey;
    protected String configKey;
    protected Class<?> type;
    protected T value;
    protected T defaultValue;
    protected String description;
    protected String errorMessage;

    protected AbstractArgParser() {

    }

    protected AbstractArgParser(String name, String description, String shortKey, String longKey, String configKey) {
        this.name = name;
        this.shortKey = shortKey;
        this.longKey = longKey;
        this.description = description;
        this.configKey = configKey;
    }

    public abstract T parse(String strValue);

    public abstract boolean validate(String strValue);

    /**
     * @return the longKey
     */
    public String getLongKey() {
        return longKey;
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getDescription() {
        return String.format("[%s/%s] : %s ( default:%s )", shortKey, longKey, description, defaultValue);
    }

    @Override
    public String getErrorMessage(Object[] args) {
        return String.format(errorMessage, args);
    }

    @Override
    public String getShortKey() {
        return shortKey;
    }

    @Override
    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public String getConfigKey() {
        return configKey;
    }

    public String writeToLine() {
        return configKey + "=" + value;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

}