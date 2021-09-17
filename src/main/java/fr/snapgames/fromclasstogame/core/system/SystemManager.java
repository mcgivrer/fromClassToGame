package fr.snapgames.fromclasstogame.core.system;

import fr.snapgames.fromclasstogame.core.Game;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The SystemManager will contain and manage all available System in te Game.
 * you can add or remove a system at game start.
 */

public class SystemManager {
    private static Map<Class<? extends System>, System> systemInstances;
    private static SystemManager instance;
    private static Game game;

    private SystemManager(Game g) {
        game = g;
        systemInstances = new ConcurrentHashMap<>();
    }

    public static SystemManager initialize(Game g) {
        if (instance == null) {
            instance = new SystemManager(g);
        }
        return instance;
    }

    public static System get(Class<? extends System> systemClass) {
        if (systemInstances.containsKey(systemClass)) {
            return systemInstances.get(systemClass);
        }
        return null;
    }

    public static void add(Class<? extends System> systemClass) {
        try {
            System systemInstance = systemClass.getConstructor(Game.class).newInstance(game);
            systemInstances.put(systemClass, systemInstance);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static SystemManager getInstance() {
        return instance;
    }

    public static void remove(Class<? extends System> s) {
        systemInstances.remove(s);
    }

    public static Collection<System> getSystems() {
        return systemInstances.values();
    }

    public static void dispose() {
        systemInstances.entrySet().stream().forEach(e -> {
            e.getValue().dispose();
            systemInstances.remove(e.getKey());
        });
    }
}
