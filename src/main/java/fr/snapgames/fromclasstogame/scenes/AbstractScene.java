package fr.snapgames.fromclasstogame.scenes;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.snapgames.fromclasstogame.Game;
import fr.snapgames.fromclasstogame.GameObject;
import fr.snapgames.fromclasstogame.Scene;
import fr.snapgames.fromclasstogame.io.exception.UnknownResource;

public abstract class AbstractScene implements Scene {

    protected Map<String, GameObject> objects = new HashMap<>();
    protected List<GameObject> objectsList = new ArrayList<>();

    protected Game game;
    protected int debug = 0;

    @Override
    public void initialize(Game g) {
        this.game = g;
    }

    @Override
    public void create(Game g) throws UnknownResource {

    }

    @Override
    public void activate() {

    }

    @Override
    public void update(long dt) {

        objectsList.forEach(e -> {
            e.update(dt);
        });
    }

    public void add(GameObject go) {
        if (!objects.containsKey(go.name)) {
            objects.put(go.name, go);
            objectsList.add(go);
            game.getRender().add(go);
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
        return objectsList.stream().filter(o -> o.name.contains(filteredName)).collect(Collectors.toList());
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
            case KeyEvent.VK_R:
                activate();
                break;

            default:
                break;
        }

    }

}
