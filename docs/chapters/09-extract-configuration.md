# U0900 Extracting the Configuration

As we have already seen in the previous chapter, we split and refactor the Game class from beginning. Here is the step
where we split the configuration management from the main Game class.

The operation is quietly simple, we just have to create a dedicated class, and move all thing about configuration from
the Game class to the new Configuration one.

## A Configuration object

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
}
```

We also need a goo constructor to initialize things

## U0901 reading properties

THe configration values are from a properties file.

```java
public class Configuration {
    //...
    public Configuration(){

        defaultConfig=ResourceBundle.getBundle("config");
        readValuesFromFile();
        }
    //...
}
```

And need to populate, as before, the attributes with the default values.

```java
public class Configuration {
    //...
    public void readValuesFromFile() {

        this.width = Integer.parseInt(defaultConfig.getString("game.setup.width"));
        this.height = Integer.parseInt(defaultConfig.getString("game.setup.height"));
        this.scale = Double.parseDouble(defaultConfig.getString("game.setup.scale"));
        this.FPS = Double.parseDouble(defaultConfig.getString("game.setup.fps"));
        this.title = defaultConfig.getString("game.setup.title");
        this.scenes = defaultConfig.getString("game.setup.scenes");
        this.defaultScene = defaultConfig.getString("game.setup.scene.default");
    }
    //...
}
```

## U902 Parsing arguments

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

### U0903 Wrong argument

In case of wrong argument, an `UnknownArgumentException` is thrown.

### Updating objects using configuration

And the last but not least, Add a `configuration` attributes to the `Game` class, and the famous `getConfiguration()`,
to let other class access those values:

```java
public class Game implements KeyListener {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    //...
    private Configuration configuration;
    //...

    public Configuration getConfiguration() {
        return this.configuration;
    }
}
```

And typically from the SceneManager, change the right line :

```java
public class SceneManager {
    //...

    public void activate() {
        activate(game.getConfiguration().defaultScene);
    }
    //...
}
```

You now have a super configuration class you may have to update to add new things.

## A possible enhancement

If you really want to get an 'infinite' version of the configuration system, you can also move all attributes to a Map,
and dynamically configure those attributes at Start, and then get there values through the Map get.

The good solution will be to implement an internal HashMap for type dedicated parser according to the attributes you
want to man,age into your configuration.

Reading the `config.property` file into
a [`ResourceBundle`](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html "see the official ResourceBundle Java documentation")
, then apply some Integer, Double, String or Boolean parser to each of the values to retrieve default configured one,
and store them into a Value map.

```java
public class Configuration {
    private ResourceBundle default;
    private Map<String, Object> values;

    // load default values from configuration file
    Configuration(String configurationPath) {
        default =ResourceBundle.getBundle(configurationPath);
        values = new HashMap<>();
        readDefaultValues();
    }

    // parse the Java CLI arguments
    public void parseArgs(String[] argv) {
        //...
    }

    // extract default values from Configuration file
    public void readDefaultValues() {...}

    // configuration value accessor.
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

## Evolution

The Configuration class will integrate progressively more and more parameters to be set and loaded from the properties
file.

The best way to add new attributes to the configuration will be to add some helpers on definition and on parsing vaue,
but also on help side. Requesting from the CLI, you would get a well formated and documented help bout possible
parameters and their values.

You would get some `ArgParser` interface and a `CLIManager` to support those parser.

### the IArgParser interface

```java
public interface IArgParser<T> {
    public boolean validate(String strValue);

    public T getValue();

    public String getShortKey();

    public String getLongKey();

    public String getName();

    public String getDescription();

    public String getErrorMessage(Object[] args);

    public T getDefaultValue();
}
```

In this interface, the main intersting thing are name and description, useful to generate the help on the cli. Name will
be the `name` of the parameter, and `description` will help understanding its usage.

An abstract class will provide a default implmentation for all parsers.

```java
public abstract class ArgParser<T> implements IArgParser<T> {

    public String name;
    public String shortKey;
    public String longKey;
    public Class<?> type;
    public T value;
    public T defaultValue;
    public String description;
    public String errorMessage;

    protected ArgParser() {

    }

    protected ArgParser(String name, String shortKey, String longKey, String description) {
        this.name = name;
        this.shortKey = shortKey;
        this.longKey = longKey;
        this.defaultValue = defaultValue;
        this.description = description;
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
}
```

And the CLIManager will be used to parse command line interface parameters, but also to provide the help text.

```java
public class CliManager {
    @SuppressWarnings("unused")
    private Game game;
    private Map<String, IArgParser<?>> argParsers = new HashMap<>();
    private Map<String, Object> values = new HashMap<>();

    public CliManager(Game g) {
        this.game = g;
    }

    public void add(IArgParser<?> ap) {
        argParsers.put(ap.getName(), ap);
        log.debug("add cli parser for " + ap.getDescription());
    }

    public void parse(String[] args) {
        for (String arg : args) {
            if (arg.equals("h") || arg.equals("help")) {
                System.out.println("\n\nCommand Usage:\n--------------");
                for (IArgParser<?> ap : argParsers.values()) {
                    System.out.println("- " + ap.getDescription());
                }
                System.exit(0);
            } else {
                parseArguments(arg);
            }
        }
    }

    private void parseArguments(String arg) {
        String[] itemValue = arg.split("=");
        for (IArgParser<?> ap : argParsers.values()) {
            if (ap.getShortKey().equals(itemValue[0]) || ap.getLongKey().equals(itemValue[0])) {
                if (ap.validate(itemValue[1])) {
                    values.put(ap.getName(), ap.getValue());
                } else {
                    log.error(ap.getErrorMessage(null));
                }
            }
        }
    }

    public Object getValue(String key) throws ArgumentUnknownException {
        if (values.containsKey(key)) {
            return values.get(key);
        } else {
            return argParsers.get(key).getDefaultValue();
        }
    }

    public boolean isExists(String key) {
        return values.containsKey(key);
    }
}
```

### Implementing a parser

An Integer parameter will have to parse int values.

```java
public class IntArgParser extends ArgParser<Integer> {

    public IntArgParser() {
        super();
    }

    public IntArgParser(
            String name,
            String shortKey,
            String longKey,
            int defaultValue,
            int min,
            int max,
            String description,
            String errorMessage) {
        super(name, shortKey, longKey, defaultValue, min, max, description, errorMessage);
    }

    @Override
    public boolean validate(String strValue) {
        value = defaultValue;
        try {
            value = parse(strValue);
            if ((min != null && value < min) || (max != null && value > max)) {
                errorMessage += String.format(
                        "value for %s must be between %s and %s. Value has been limited to min/max", name, min, max,
                        defaultValue);
                value = (value < min ? min : (value > max ? max : value));
            }
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
        6
        int value = Integer.parseInt(strValue);
        return value;
    }
}
```

The same way to implement Boolean, Double and Float will be used.

- `BooleanArgParser` will parse a boolean value parameter,
- `DoubleArgParser` will parse a double value parameter,
- `FloatArgParser` will parse a float value parameter.

And specific one will be

- `IntArrayArgParser` will parse a list of int values parameter.

And a final pass would provide a way to load/save those parameters to a configuration file.

But this is another story !
