package fr.snapgames.fromclasstogame.core.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.behaviors.DebugSwitcherBehavior;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.World;
import fr.snapgames.fromclasstogame.core.scenes.transition.AbstractSceneTransition;
import fr.snapgames.fromclasstogame.core.scenes.transition.FadeToBlackCentricCircleTransition;
import fr.snapgames.fromclasstogame.core.scenes.transition.Transition;
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
 * Any new custom action can bee added to the {@link ActionHandler} with an action code from ACTION_CUSTOM (= 200).
 * <strong></strong>
 */
public abstract class AbstractScene implements Scene {


    private static final Logger logger = LoggerFactory.getLogger(AbstractScene.class);
    protected Map<String, GameObject> objects = new HashMap<>();
    protected List<GameObject> objectsList = new ArrayList<>();
    protected List<GameObject> objectsCameraRelativeList = new ArrayList<>();
    protected List<Behavior<Scene>> behaviors = new ArrayList<>();
    protected List<Transition<Render, Scene>> transitionList = new ArrayList<>();

    protected World world;

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
        // Add the Debug switcher capability to this scene
        addBehavior(new DebugSwitcherBehavior());
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
                //game.getRender().moveFocusToCamera((Camera) go);
            }
            if (activeCamera == null) {
                activeCamera = (Camera) go;
            }
        } else if (!objects.containsKey(go.name)) {
            objects.put(go.name, go);
            ((Render) SystemManager.get(Render.class)).add(this, go);
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
                game.getRender().moveFocusToCamera(null);
            }
            if (activeCamera.equals(go)) {
                activeCamera = null;
            }
        } else if (objects.containsKey(go.name)) {
            objects.remove(go.name);
            objectsList.remove(go);
            SystemManager.remove(go);
        }
    }

    public GameObject getGameObject(String name) {
        return objects.get(name);
    }

    /**
     * find GameObject filtered on their name according to a filteredName.
     *
     * @param filteredName
     * @return
     */
    public List<GameObject> find(String filteredName) {
        return objectsList.stream().filter(
                o -> o.name.contains(filteredName)
        ).collect(Collectors.toList());
    }

    public List<GameObject> getObjectsList() {
        return objectsList;
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
            objects.forEach((k, o) -> {
                if (!o.behaviors.isEmpty()) {
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
        objects.forEach((k, o) -> {
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

    @Override
    public void setName(String name) {
        this.sceneName = name;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void render(Render r) {

    }

    /**
     * Define the World context for this scene.
     *
     * @param w the World context to be applied to the scene.
     * @return
     */
    public AbstractScene setWorld(World w) {
        this.world = w;
        return this;
    }

    public World getWorld() {
        return this.world;
    }

    @Override
    public List<Behavior<Scene>> getBehaviors() {
        return behaviors;
    }

    public List<GameObject> getObjectsCameraRelativeList() {
        return objectsCameraRelativeList;
    }

    /**
     * return true of the Scene has some transition.
     *
     * @return
     */
    public boolean hasTransition() {
        return !transitionList.isEmpty();
    }

    public List<Transition<Render, Scene>> getTransitionList() {
        return this.transitionList;
    }

    public void addTransition(Transition<Render, Scene> st) {
        this.transitionList.add(st);

    }


}




