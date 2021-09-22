package fr.snapgames.fromclasstogame.core.system;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;

import java.util.ArrayList;
import java.util.List;

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
 * @since 1.1
 */
public abstract class System {

    protected Game game;

    protected List<GameObject> objects = new ArrayList<>();

    public abstract String getName();

    public System(Game g) {
        this.game = g;
    }

    public abstract int initialize(Configuration config);

    public abstract void dispose();

    public boolean isReady(){
        return true;
    }
}
