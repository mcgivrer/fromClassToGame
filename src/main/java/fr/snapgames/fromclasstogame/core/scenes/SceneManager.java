package fr.snapgames.fromclasstogame.core.scenes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link SceneManager} is a game states switcher to activate one of the
 * different state of a game, from the Title scree, to the Menu and the Invenrty
 * or the PLay screen.
 */
public class SceneManager {
    private final static Logger logger = LoggerFactory.getLogger(SceneManager.class);

    Map<String, Class<?>> scenesClasses = new HashMap<>();
    Map<String, Scene> scenesInstances = new HashMap<>();

    private Game game;

    private Scene current;

    public SceneManager(Game g) {
        this.game = g;
    }

    /**
     * <p>
     * Intialize classes from a simple String array.
     * <p>
     * Each string are purposed to provide a name and a class with its full path:
     * <ul>
     * <li><code>code</code>:<code>class</code></li>
     * <li><code>test</code>:<code>fr.snapgames.fromclasstogame.test.TestScene</code></li>
     * </ul>
     * 
     * @param sceneClasses a semicolumn formated String array definig the list of
     *                     scene to be managed.
     */
    public void initialize(String[] sceneClasses) {
        for (String scnClass : sceneClasses) {
            addScene(scnClass);
        }
    }

    public void addScene(String scnClass) {
        String[] scn = scnClass.split(":");
        if (scn.length == 2) {
            Class<?> s;
            try {
                s = Class.forName(scn[1]);
                scenesClasses.put(scn[0], s);
            } catch (ClassNotFoundException e) {
                logger.error("Unable to load class {}", scnClass);
            }
        }
    }
    public void activate(){
        activate(game.getConfiguration().defaultScene);
    }
    /**
     * <p>
     * Activate a specific {@link Scene} from its internal name
     * {@link Scene#getName()} of the semicolumn delimited String array from the
     * initialize() see {@link SceneManager#initialize(String[])}).
     * 
     * <p>
     * The {@link Scene#initialize(Game)} and the {@link Scene#create(Game)} methods
     * will be called if not already initialized. Finally, the
     * {@link Scene#activate()} si called.
     * 
     * @param name name of the scene to be activated.
     */
    public void activate(String name) {
        if (scenesClasses.containsKey(name)) {
            Scene s;
            try {
                if (scenesInstances.containsKey(name)) {
                    s = scenesInstances.get(name);
                } else {
                    s = (Scene) scenesClasses.get(name).newInstance();
                    s.initialize(game);
                    scenesInstances.put(s.getName(), s);
                    s.create(game);
                }
                this.current = s;
                s.activate();

            } catch (InstantiationException | IllegalAccessException | UnknownResource e) {
                logger.error("Unable to instantiate class {}", name);
            }
        }

    }

    /**
     * This is an adding possibility to add dynaically a new Scene prgrammatically.
     * 
     * @param s the instance of the {@link Scene} to be added.
     */
    public void add(Scene s) {
        scenesInstances.put(s.getName(), s);
    }

    /**
     * Dispose all scenes from the cache.
     */
    public void dispose() {
        for (Scene s : scenesInstances.values()) {
            s.dispose();
        }
        scenesInstances.clear();
        scenesClasses.clear();
    }

    public Scene getCurrent() {
        return this.current;
    }

    public Collection<?> getScenes() {
        return scenesClasses.values();
    }
    public Collection<?> getScenesInstances() {
        return scenesInstances.values();
    }

}