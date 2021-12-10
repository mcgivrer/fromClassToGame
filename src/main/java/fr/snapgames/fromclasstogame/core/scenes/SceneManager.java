package fr.snapgames.fromclasstogame.core.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@link SceneManager} is a game states switcher to activate one of the
 * different state of a game, from the Title scree, to the Menu and the Invenrty
 * or the PLay screen.
 */
public class SceneManager extends System {
    private final static Logger logger = LoggerFactory.getLogger(SceneManager.class);

    Map<String, Class<?>> scenesClasses = new ConcurrentHashMap<>();
    Map<String, Scene> scenesInstances = new ConcurrentHashMap<>();

    private Scene current;

    public SceneManager(Game g) {
        super(g);
    }

    @Override
    public String getName() {
        return "Scene Manager";
    }

    @Override
    public int initialize(Configuration config) {
        initialize(config.scenes.split(","));
        return 0;
    }

    /**
     * <p>
     * Initialize classes from a simple String array.
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

    /**
     * Add a scene as class name to the available ones.
     *
     * @param scnClass the full name of the class.
     */
    public void addScene(String scnClass) {
        String[] scn = scnClass.split(":");
        if (scn.length == 2) {
            Class<?> s;
            try {
                s = Class.forName(scn[1]);
                scenesClasses.put(scn[0], s);
                logger.info("Add Scene {} as {}", scn[0], scn[1]);
            } catch (ClassNotFoundException e) {
                logger.error("Unable to load class {}", scnClass);
            }
        }
    }

    /**
     * Activate the default scene (create the instance if it does not exist).
     */
    public void activate() {
        String defaultScene = game.getConfiguration().defaultScene;
        logger.debug("Activate the default scene '" + defaultScene + "'");
        activate(defaultScene);
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
            if (scenesInstances.containsKey(name)) {
                s = scenesInstances.get(name);
            } else {
                s = instantiateScene(name);
            }
            if (s != null) {
                s.setName(name);
                setCurrent(s);
            }
        }

    }

    /**
     * Create an instance of the Scene named name.
     *
     * @param name the name of the scene to be instantiated
     * @return the instance of the Scene.
     */
    private Scene instantiateScene(String name) {
        Scene s = null;
        try {
            Class<?> clazzScene = scenesClasses.get(name);
            final Constructor<?> sceneConstructor = clazzScene.getConstructor(Game.class);
            s = (Scene) sceneConstructor.newInstance(game);
            add(name, s);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            logger.error("Unable to instantiate class {}", name);
        }
        return s;
    }

    /**
     * This is an adding possibility to add dynamically a new Scene programmatically.
     *
     * @param s the instance of the {@link Scene} to be added.
     */
    public void add(String name, Scene s) {
        try {
            scenesInstances.put(name, s);
            scenesClasses.put(name, s.getClass());
            s.initialize(game);
            s.create(game);
        } catch (UnknownResource e) {
            e.printStackTrace();
        }
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

    /**
     * Return the current active Scene. It is activated if not already done.
     *
     * @return the current scene instance.
     */
    public synchronized Scene getCurrent() {
        if (current == null) {
            activate();
        }
        return this.current;
    }

    /**
     * request for the Scene s to be activated.
     *
     * @param s
     */
    private void setCurrent(Scene s) {
        if (s != null) {
            this.current = s;
            s.activate();
        }
    }

    /**
     * delegate input operation to the current scene and to its declared behaviors.
     *
     * @param ah the ActionHandler to support input.
     */
    public void input(ActionHandler ah) {
        getCurrent().input(ah);
        for (Behavior<Scene> b : getCurrent().getBehaviors()) {
            b.onInput(getCurrent(), ah);
        }
    }

    /**
     * Delegate the rendering operation to the current scene and its possible declared Behaviors
     *
     * @param r the Render system to delegate rendering pipeline operations.
     */
    public void render(Render r) {
        getCurrent().render(r);
        for (Behavior<Scene> b : getCurrent().getBehaviors()) {
            b.onRender(getCurrent(), r);
        }

    }

    /**
     * Delegate update operation to the current scene and its possible declared Behaviors
     *
     * @param dt the elapsed time since previous call.
     */
    public void update(long dt) {
        getCurrent().update(dt);
        for (Behavior<Scene> b : getCurrent().getBehaviors()) {
            b.onUpdate(getCurrent(), dt);
        }
    }

    /**
     * Delegate the Action processing operation to the current scene and its possible declared Behaviors.
     *
     * @param action the actoin cade to be processed.
     */
    public void onAction(Integer action) {
        getCurrent().onAction(action);
        for (Behavior<Scene> b : getCurrent().getBehaviors()) {
            b.onAction(getCurrent(), action);
        }
    }

    /**
     * retrieve list of Scenes.
     *
     * @return the current list of scenes classes.
     */
    public Collection<Class<?>> getScenes() {
        return scenesClasses.values();
    }

    /**
     * Retrieve the list of Scene instances
     *
     * @return a collectoin of Scene instances
     */
    public Collection<Scene> getScenesInstances() {
        return scenesInstances.values();
    }

    /**
     * retrieve the existing scene instance corresponding to the name <code>sceneName</code>.
     *
     * @param sceneName the name of the scene to get.
     * @return
     */
    public Scene getScene(String sceneName) {
        return scenesInstances.get(sceneName);
    }
}
