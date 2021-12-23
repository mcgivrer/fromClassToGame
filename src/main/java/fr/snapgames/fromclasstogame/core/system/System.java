package fr.snapgames.fromclasstogame.core.system;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.scenes.Scene;

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

    protected Game game;

    protected List<GameObject> objects = new CopyOnWriteArrayList<>();

    public System(Game g) {
        this.game = g;
    }

    public abstract String getName();

    public abstract int initialize(Configuration config);

    public abstract void dispose();

    public boolean isReady() {
        return true;
    }

    public synchronized void add(GameObject o) {
        if (!objects.contains(o)) {
            objects.add(o);
        }
    }

    public void clearObjects() {
        objects.clear();
    }

    public synchronized void remove(GameObject o) {
        if (objects.contains(o)) {
            objects.remove(o);
        }
    }

    public synchronized List<GameObject> getObjects() {
        return objects;
    }


    public Game getGame() {
        return this.game;
    }
}
