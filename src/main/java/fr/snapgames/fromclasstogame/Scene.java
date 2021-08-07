package fr.snapgames.fromclasstogame;

import fr.snapgames.fromclasstogame.io.exception.UnknownResource;

import java.util.List;
import java.awt.event.KeyListener;

/**
 * The {@link Scene} interface to create a new game state.
 * 
 * @author Frédéric Delorme
 * @since 2021-08-02
 */
public interface Scene extends KeyListener{

    String getName();

    void initialize(Game g);

    void create(Game g) throws UnknownResource;

    void add(GameObject go);

    GameObject getGameObject(String name);

    List<GameObject> find(String filteredName);

    List<GameObject> getObjectsList();

    void activate();

    void update(long dt);

    void input();

    void render();

    void dispose();

}
