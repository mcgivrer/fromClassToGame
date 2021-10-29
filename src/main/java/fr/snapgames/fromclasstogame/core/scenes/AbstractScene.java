package fr.snapgames.fromclasstogame.core.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractScene implements Scene {
    private static final Logger logger = LoggerFactory.getLogger(AbstractScene.class);

    protected Map<String, GameObject> objects = new HashMap<>();
    protected List<GameObject> objectsList = new ArrayList<>();
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
                game.getRender().setCamera((Camera) go);
            }
            if (activeCamera == null) {
                activeCamera = (Camera) go;
            }
        } else if (!objects.containsKey(go.name)) {
            objects.put(go.name, go);
            objectsList.add(go);
            SystemManager.add(go);
        }
    }

    public void remove(GameObject go) {
        if (go.getClass().getName().equals(Camera.class.getName())) {
            if (cameras.containsKey(go.name)) {
                cameras.remove(go.name);
                game.getRender().setCamera(null);
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

    public double rand(double min, double max) {
        return (Math.random() * (max - min)) + min;
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
            case KeyEvent.VK_D:
                this.debug = this.debug < 5 ? this.debug + 1 : 0;
                game.getWindow().setDebug(debug);
                break;
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

    public Camera getCamera(String cameraName) {
        return cameras.get(cameraName);
    }

    public void setActiveCamera(Camera c) {
        activeCamera = cameras.get(c.name);
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

    public void onAction(ActionHandler.ACTIONS a) {
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

}
