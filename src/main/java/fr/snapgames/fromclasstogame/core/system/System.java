package fr.snapgames.fromclasstogame.core.system;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.World;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Default System.
 * <p>
 * A System will be managed by the SystemManager and will provide mechanism to interact with this.
 * <p>
 * - A default `System(Game)` constructor with a Game as parameter,
 * - an `initialize(Config)` to start the System,
 * - a `dispose()` method to free all system needed resources.
 *
 * @author Frédéric Delorme
 * @since 1.0.0
 */
public abstract class System {
    /**
     * Reference to the parent game.
     */
    protected Game game;

    /**
     * Internal list of Object for the system.
     */
    protected List<GameObject> objects = new CopyOnWriteArrayList<>();
    /**
     * the world object to be used by the Render
     */
    protected World world;

    /**
     * Create a new System.
     *
     * @param g linked to the parent Game g.
     */
    public System(Game g) {
        this.game = g;
    }

    /**
     * provide the System internal name.
     *
     * @return
     */
    public abstract String getName();

    /**
     * initialize the System
     *
     * @param config
     * @return
     */
    public abstract int initialize(Configuration config);

    /**
     * Dispose possible loaded resources into the System.
     */
    public abstract void dispose();

    /**
     * Is the system ready to run ?
     *
     * @return
     */
    public boolean isReady() {
        return true;
    }

    /**
     * Add a {@link GameObject} to th system.
     *
     * @param o the GameObject to be maintained to the {@link System}.
     */
    public synchronized void add(GameObject o) {
        if (!objects.contains(o)) {
            objects.add(o);
        }
    }

    /**
     * Reset all {@link GameObject} from the objects list.
     */
    public void clearObjects() {
        objects.clear();
    }

    /**
     * Remove the {@link GameObject} o from the internal list of objects.
     *
     * @param o the {@link GameObject} to be removed from the System.
     */
    public synchronized void remove(GameObject o) {
        if (objects.contains(o)) {
            objects.remove(o);
        }
    }

    /**
     * Retrieve the internal list of {@link GameObject}.
     *
     * @return
     */
    public synchronized List<GameObject> getObjects() {
        return objects;
    }


    /**
     * return the parent game.
     *
     * @return
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Set the parent World object.
     *
     * @param world
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Retrieve an object from the internal {@link SystemManager#context}.
     *
     * @param key
     * @return
     */
    public Object getFromContext(String key) {
        return SystemManager.getFromContext(key);
    }

    /**
     * Add an object in to the {@link SystemManager#context} with its key name.
     *
     * @param key   the key to retrieve this object in to context.
     * @param value the value object to be added to the {@link SystemManager#context}
     * @return the updated {@link System}.
     */
    public System addToContext(String key, Object value) {
        SystemManager.addToContext(key, value);
        return this;
    }
}
