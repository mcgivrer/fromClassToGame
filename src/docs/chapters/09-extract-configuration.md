---
title: From a Class to Game 
chapter: 09 - Extracting the Configuration 
author: Frédéric Delorme 
description: A dedicated service to Scenes management. 
created: 2021-08-01 
tags: gamedev, scene
---

## Extracting the Configuration

As we already seen in the previous chapter, we split and refactor the Game class from beginning. Here is the step whgere
we split the configuration management from the main Game class.

The operation is quitly simple, we just have to create a dedicated class, and move all thing about configuration from
the Game class to thenew Configuration one.

First let's create the class with its attributes:

```java
public class Configuration {

    private ResourceBundle defaultConfig;

    public String title = "fromClassToGame";

    public int width = 320;
    public int height = 200;
    public double scale = 1.0;

    public double FPS = 60;

    public String scenes = "";
    public String defaultScene = "";
```

We also need a goo constructor to initialize things

```java
    public Configuration(){

        defaultConfig=ResourceBundle.getBundle("config");
        readValuesFromFile();
        }
```

And need to populate, as before, the attributes with the default values.

```java
    public void readValuesFromFile(){

        this.width=Integer.parseInt(defaultConfig.getString("game.setup.width"));
        this.height=Integer.parseInt(defaultConfig.getString("game.setup.height"));
        this.scale=Double.parseDouble(defaultConfig.getString("game.setup.scale"));
        this.FPS=Double.parseDouble(defaultConfig.getString("game.setup.fps"));
        this.title=defaultConfig.getString("game.setup.title");
        this.scenes=defaultConfig.getString("game.setup.scenes");
        this.defaultScene=defaultConfig.getString("game.setup.scene.default");
        }
```

And Finally, we will parse the java command line arguments to extract possible user values:

```java
class Configuration {
    public Configuration parseArgs(String[] argv) throws UnknownArgumentException {
        if (argv != null) {
            for (String arg : argv) {
                String[] values = arg.split("=");
                switch (values[0].toLowerCase()) {
                    case "width":
                        this.width = Integer.parseInt(values[1]);
                        break;
                    case "height":
                        this.height = Integer.parseInt(values[1]);
                        break;
                    case "scale":
                        this.scale = Double.parseDouble(values[1]);
                        break;
                    case "fps":
                        this.FPS = Double.parseDouble(values[1]);
                        break;
                    case "title":
                        this.title = values[1];
                        break;
                    case "scene":
                        this.defaultScene = values[1];
                        break;
                    default:
                        throw new UnknownArgumentException(String.format("The argument %s is unknown", arg));
                }
            }
        }
        return this;
    }
}
```

And the last but not least, Add a `configuration` attributes to the `Game` class, and the famous `getConfiguration()`,
to let other class access those values:

```java
public class Game implements KeyListener {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    ...
    private Configuration configuration;
    ...

    public Configuration getConfiguration() {
        return this.configuration;
    }
}
```

And typically from the SceneManager, change the right line :

```java
public class SceneManager {
    ...

    public void activate() {
        activate(game.getConfiguration().defaultScene);
    }
    ...
}
```

You now have a super configuration class you may have to update to add new things.

### A possible enhancement

If you really want to get an 'infinit' version of the configuration system, you can also move all attributes to a Map,
and dynamically configure those attributes at Start, and then get there values through the Map get.

The good solution will be to implement an internal HashMap for type dedicated parser according to the attributes you
want to man,age into your configuration.

Reading the `config.property` file into
a [`ResourceBundle`](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html "see the official ResourceBundle Java documentation")
, then apply some Interget, Double, String or Boolean parser to each of the values to retrieve default configured one,
and stoire them into a Value map.

```java
public class Configuration {
    private ResourceBundle default;
    private Map<String, Object> values;

    // load default values from configuration file
    Configuration(String configurationPath) {
        default = ResourceBundle.getBundle(configurationPath);
        values = new HashMap<>();
        readDefaultValues();
    }

    // parse the Java CLI arguments
    public void parseArgs(String[] argv) {
        ...
    }

    // extract default values from Configuration file
    public void readDefaultValues() {...}

    // configuration value accesseur.
    public String getString(String name) {
        return values.get(name);
    }

    public Boolean getBoolean(String name) {
        return Boolean.parseBoolean(values.get(name));
    }

    public Integer getInteger(String name) {
        return Integer.parseInt(values.get(name));
    }

    public Double getDouble(String name) {
        return Double.parseDouble(values.get(name));
    }

    public String[] getArrayString(String name) {
        return values.get("name").split(',');
    }
}
```

