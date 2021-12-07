package fr.snapgames.fromclasstogame.core.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;

import java.util.List;

/**
 * The {@link Scene} interface to create a new game state.
 *
 * @author Frédéric Delorme
 * @since 2021-08-02
 */
public interface Scene extends ActionHandler.ActionListener {

    String getName();

    void initialize(Game g);

    void create(Game g) throws UnknownResource;

    void add(GameObject go);

    GameObject getGameObject(String name);

    List<GameObject> find(String filteredName);

    List<GameObject> getObjectsList();

    Camera getActiveCamera();

    void activate();

    void update(long dt);

    void input(ActionHandler actionHandler);

    void onAction(Integer action);

    void render(Render render);

    void dispose();

    Camera getCamera(String cameraName);

    List<Behavior<Scene>> getBehaviors();

    Game getGame();

    void setName(String name);
}
