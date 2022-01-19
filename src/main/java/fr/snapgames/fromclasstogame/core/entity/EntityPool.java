package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.Game;

import java.util.Arrays;
import java.util.List;

public class EntityPool {

    private final String DEFAULT_NAME = "ep_";
    private static long index = 1;
    protected String name;
    protected List<GameObject> entities;

    public EntityPool() {
        this.name = DEFAULT_NAME + (index++);
    }

    public EntityPool(String name, int size) {
        this.name = name;
        this.entities = Arrays.asList(new GameObject[size]);
    }

    public String getName() {
        return this.name;
    }

    /**
     * return the next inactive object to be reused else -1
     *
     * @return
     */
    private int getIndexNextFree() {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) == null || !entities.get(i).isActive()) {
                return i;
            }
        }
        return -1;
    }

    private int find(GameObject go) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).name.equals(go.name)) {
                return i;
            }
        }
        return -1;
    }

    public void add(GameObject go) {
        int i = getIndexNextFree();
        if (i != -1) {
            entities.set(i, go);
        }
    }

    public void remove(GameObject go) {
        int i = find(go);
        if (i != -1) {
            entities.get(i).setActive(false);
        }
    }

    public GameObject get(String name) throws UnkownGameObject {
        for (int i = 0; i < entities.size(); i++) {
            GameObject g = entities.get(i);
            if (g.name.equals(name)) {
                return g;
            }
        }
        throw new UnkownGameObject(name);
    }

    public int getSize() {
        return entities.size();
    }

    public List<GameObject> getEntities() {
        return this.entities;
    }
}