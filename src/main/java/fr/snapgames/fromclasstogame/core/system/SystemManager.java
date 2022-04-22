package fr.snapgames.fromclasstogame.core.system;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The SystemManager will contain and manage all available System in te Game.
 * you can add or remove a system at game start.
 *
 * @author Frédéric Delorme
 * @since 0.0.1
 */
public class SystemManager {
    /**
     * THe map hosting all the systems
     */
    private static Map<Class<? extends System>, System> systemInstances;

    private static Map<String, Object> context = new ConcurrentHashMap<>();
    /**
     * the singleton instance for this SystemManager.
     */
    private static SystemManager instance;
    /**
     * the parent Game object the SystemManager is attached to.
     */
    private static Game game;

    /**
     * internal cinstructor for the SystemManager.
     *
     * @param g
     */
    private SystemManager(Game g) {
        game = g;
        systemInstances = new ConcurrentHashMap<>();
    }

    /**
     * Initialize the SystemManager.
     *
     * @param g the parent Game the SystemManager is used for.
     * @return the initialized SystemManager.
     */
    public static SystemManager configure(Game g) {
        if (instance == null) {
            instance = new SystemManager(g);
        }
        return instance;
    }

    /**
     * Retrieve a System instance from its map of systems.
     *
     * @param systemClass the System class to retrieve its corresponding instance.
     * @return the instance of the corresponding System
     */
    public static System get(Class<? extends System> systemClass) {
        Optional<Map<Class<? extends System>, System>> oSystemInstances = Optional.ofNullable(systemInstances);
        if (oSystemInstances.isPresent() && systemInstances.containsKey(systemClass)) {
            return systemInstances.get(systemClass);
        }
        return null;
    }

    /**
     * Add a System to be managed by the SystemManager service.
     *
     * @param systemClass a system class to be instantiated and managed by the SystemManager.
     */
    public static void add(Class<? extends System> systemClass) {
        try {
            System systemInstance = systemClass.getConstructor(Game.class).newInstance(game);
            systemInstances.put(systemClass, systemInstance);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * retrieve the current instance of the SystemManager.
     *
     * @return the instance of the SystemManager.
     */
    public static SystemManager getInstance() {
        return instance;
    }

    /**
     * Remove the system from the manager.
     *
     * @param s
     */
    public static void remove(Class<? extends System> s) {
        Optional<Map> oSystemInstances = Optional.ofNullable(systemInstances);
        if (oSystemInstances.isPresent()) {
            systemInstances.remove(s);
        }
    }

    /**
     * Retrieve all systems from the system manager.
     *
     * @return a collection of systems contained by this manager.
     */
    public static Collection<System> getSystems() {
        return systemInstances.values();
    }

    /**
     * Broadcast the configuration to all the Systems.
     *
     * @param config the Configuration provided but the game to all systems for initialization purpose.
     */
    public static void configure(Configuration config) {
        Optional<Map> oSystemInstances = Optional.ofNullable(systemInstances);
        if (oSystemInstances.isPresent()) {
            systemInstances.entrySet().forEach(e -> {
                e.getValue().initialize(config);
            });
        }
    }

    /**
     * Dispose all resources handled by all the systems.
     */
    public static void dispose() {
        Optional<Map> oSystemInstances = Optional.ofNullable(systemInstances);
        if (oSystemInstances.isPresent()) {
            systemInstances.entrySet().stream().forEach(e -> {
                e.getValue().dispose();
                systemInstances.remove(e.getKey());
            });
        }
    }

    /**
     * Add an object to the all the Systems declared in the SystemManager.
     *
     * @param gameObject the GameObject to be added to all the systems. This GameObject will be processed by all the declared Systems.
     */
    public static void add(GameObject gameObject) {
        Optional<Map> oSystemInstances = Optional.ofNullable(systemInstances);
        if (oSystemInstances.isPresent()) {
            systemInstances.entrySet().forEach(e -> {
                e.getValue().add(gameObject);
            });
        }
    }

    /**
     * Remove a GameObject from all the systems.
     *
     * @param gameObject the GameObject to be removed from scope of all the Systems.
     */
    public static void remove(GameObject gameObject) {
        Optional<Map> oSystemInstances = Optional.ofNullable(systemInstances);
        if (oSystemInstances.isPresent()) {
            systemInstances.entrySet().forEach(e -> {
                e.getValue().remove(gameObject);
            });
        }
    }

    /**
     * Clear all GameObjects from the Systems.
     */
    public static void clearObjects() {
        Optional<Map> oSystemInstances = Optional.ofNullable(systemInstances);
        if (oSystemInstances.isPresent()) {
            systemInstances.entrySet().forEach(e -> {
                e.getValue().clearObjects();
            });
        }
    }

    /**
     * Return the number of GameObject in the SystemManager.
     *
     * @return the number of GameObject managed by the SystemManager.
     */
    public static int getNbObjects() {
        return get(SceneManager.class).objects.size();
    }

    public static void addToContext(String key, Object value) {
        context.put(key, value);
    }

    public static Object getFromContext(String key) {
        return context.get(key);
    }
}
