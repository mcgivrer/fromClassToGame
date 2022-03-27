package fr.snapgames.fromclasstogame.core.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.behaviors.DebugSwitcherBehavior;
import fr.snapgames.fromclasstogame.core.entity.*;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@link AbstractScene} implements all the basics for any Scene you want to create.
 * It also supports some DEBUG keys to display (see {@link fr.snapgames.fromclasstogame.core.gfx.renderer.AbstractRenderHelper})
 * The active keys are:
 * <strong>Debug</strong>
 * <ul>
 *     <li><kbd>D</kbd> Activate global debug mode</li>
 *     <li><kbd>TAB</kbd> next {@link GameObject} in scene's object list</li>
 *     <li><kbd>BACKSPACE</kbd> previous {@link GameObject} in scene's object list</li>
 *     <li><kbd>N</kbd> up debug level for current selected object</li>
 *     <li><kbd>B</kbd> down debug level for current selected object</li>
 *     <li><kbd>Z</kbd> reset the current scene by calling {@link Scene#create(Game)} ()}</li>
 * </ul>
 * <p>Any new custom action can bee added to the {@link ActionHandler} with an action code from ACTION_CUSTOM (= 200).</p>
 */
public abstract class AbstractScene implements Scene {

    private static final Logger logger = LoggerFactory.getLogger(AbstractScene.class);

    //TODO: replace objects and objectList by the corresponding Game System EntityPool.

    EntityPool ep = ((EntityPoolManager) SystemManager.get(EntityPoolManager.class)).get(GameObject.class.getName());

    //protected Map<String, GameObject> objects = new HashMap<>();
    //protected List<GameObject> objectsList = new ArrayList<>();
    protected List<Behavior<Scene>> behaviors = new ArrayList<>();

    protected Map<String, Camera> cameras = new HashMap<>();

    protected Camera activeCamera;

    protected String sceneName;

    protected Game game;
    protected int debug = 0;

    public AbstractScene(Game g, String name) {
        game = g;
        sceneName = name;
    }

    @Override
    public void initialize(Game g) {
        this.game = g;
    }

    @Override
    public void create(Game g) throws UnknownResource {
        game.getRenderer().clearObjects();
        // will be updated into the implemented scene
    }

    @Override
    public void activate() {

        logger.debug("Scene {} activated", this.sceneName);

    }

    @Override
    public void update(long dt) {
        if (activeCamera != null) {
            activeCamera.update(dt);
        }
    }

    public void add(GameObject go) {
        if (go.getClass().getName().equals(Camera.class.getName())) {
            if (!cameras.containsKey(go.name)) {
                cameras.put(go.name, (Camera) go);
                game.getRenderer().moveFocusToCamera((Camera) go);
            }
            if (activeCamera == null) {
                activeCamera = (Camera) go;
            }
        } else if (!ep.contains(go)) {
            ep.add(go);
            SystemManager.add(go);
        }
    }

    public void addBehavior(Behavior<Scene> b) {
        behaviors.add(b);
    }

    public void remove(GameObject go) {
        if (go.getClass().getName().equals(Camera.class.getName())) {
            if (cameras.containsKey(go.name)) {
                cameras.remove(go.name);
                game.getRenderer().moveFocusToCamera(null);
            }
            if (activeCamera.equals(go)) {
                activeCamera = null;
            }
        } else if (ep.contains(go)) {
            ep.remove(go);
            SystemManager.remove(go);
        }
    }

    public GameObject getGameObject(String name) {
        try {
            return ep.get(name);
        } catch (UnkownGameObject e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * find GameObject filtered on their name according to a filteredName.
     *
     * @param filteredName
     * @return
     */
    public List<GameObject> find(String filteredName) {
        return ep.getEntities().stream().filter(
                o -> o != null && o.name.contains(filteredName)
        ).collect(Collectors.toList());
    }

    public List<GameObject> getObjectsList() {
        return ep.getEntities();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Can be implemented in the class extending this abstract one.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Can be implemented in the class extending this abstract one.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z:
                activate();
                break;
            default:
                break;
        }
    }

    public Camera getActiveCamera() {
        return activeCamera;
    }

    public void setActiveCamera(Camera c) {
        activeCamera = cameras.get(c.name);
    }

    public Camera getCamera(String cameraName) {
        return cameras.get(cameraName);
    }

    public void input(ActionHandler ah) {
        try {
            ep.getEntities().forEach(o -> {
                if (o != null && !o.behaviors.isEmpty()) {
                    o.behaviors.forEach(b -> {
                        b.onInput(o, ah);
                    });
                }
            });
        } catch (ConcurrentModificationException e) {
            logger.error("Unable to handle input in scene '" + sceneName + "'.");
        }
    }

    public void onAction(Integer a) {
        logger.debug("Action:" + a);
        ep.getEntities().forEach(o -> {
            if (!o.behaviors.isEmpty()) {
                o.behaviors.forEach(b -> {
                    b.onAction(o, a);
                });
            }
        });
    }

    public String getName() {
        return sceneName;
    }


    public Game getGame() {
        return game;
    }


    public void draw(Renderer r) {

    }

    public List<Behavior<Scene>> getBehaviors() {
        return behaviors;
    }

    protected EntityPool getEntityPool() {
        return this.ep;
    }


    @Override
    public void setName(String name) {
        this.sceneName = name;
    }
}




