package fr.snapgames.fromclasstogame.core.entity;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * An EntityPool to manage a fixed size bunch of Entity, to manage Memory swap.
 *
 * @author Frédéric Delorme
 * @since 0.0.2
 */
public class EntityPool {

    private final String DEFAULT_NAME = "ep_";
    private static long index = 1;
    protected String name;
    //protected List<GameObject> entities;
    protected GameObject[] goArray;

    /**
     * an internal pool cinstructor to manage internal index counter.
     */
    private EntityPool() {
        this.name = DEFAULT_NAME + (index++);
    }

    /**
     * Create a new EntityPool with an entityPoolName and a fixed entityPoolSize
     *
     * @param entityPoolName the name of the EntityPool to create.
     * @param entityPoolSize the size of the internal Entity array for this new EntityPool.
     */
    public EntityPool(String entityPoolName, int entityPoolSize) {
        this();
        this.name = entityPoolName;
        goArray = new GameObject[entityPoolSize];
        //this.entities = Arrays.asList(goArray);
    }

    /**
     * Retrieve the name of this EntityPool.
     *
     * @return the name of the EntityPool.
     */
    public String getName() {
        return this.name;
    }

    /**
     * return the next inactive object to be reused else -1
     *
     * @return
     */
    private int getIndexNextFree() {
        for (int i = 0; i < goArray.length; i++) {
            if (goArray[i] == null || !goArray[i].isActive()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Retrieve an object
     *
     * @param go
     * @return
     */
    private int find(GameObject go) {
        for (int i = 0; i < goArray.length; i++) {
            if (goArray[i] != null
                    && goArray[i].name.equals(go.name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Add an object to this EntityPool.
     *
     * @param go the GameObject to be added to the pool.
     * @return the position index of this object in the EntityPool
     */
    public int add(GameObject go) {
        int i = getIndexNextFree();
        if (i != -1) {
            goArray[i] = go;
        }
        return i;
    }

    /**
     * Remove the GameObject go from the EntityPool.
     *
     * @param go
     */
    public void remove(GameObject go) {
        int i = find(go);
        if (i != -1) {
            goArray[i].setActive(false);
        }
    }

    /**
     * Retrieve the Game Object name  from the EntityPool
     *
     * @param name the name of the GameObject to be retrieved.
     * @return the corresponding GameObject.
     * @throws UnkownGameObject
     */
    public GameObject get(String name) throws UnkownGameObject {
        for (int i = 0; i < goArray.length; i++) {
            GameObject g = goArray[i];
            if (g!=null && g.getName().equals(name)) {
                return g;
            }
        }
        throw new UnkownGameObject(name);
    }

    /**
     * get the size of the EntityPool.
     *
     * @return the size of the internal array of the EntityPool.
     */
    public int getSize() {
        return goArray.length;
    }

    /**
     * Get All the GameObject from the internal array.
     *
     * @return a list of all GameObject in the internal array.
     */
    public List<GameObject> getEntities() {
        return Arrays.stream(this.goArray).filter(o -> o != null && o.isActive()).collect(Collectors.toList());
    }

    public boolean contains(GameObject go) {
        return find(go) != -1;
    }

    /**
     * Remove all object from the pool.
     */
    public void clear() {
        for (int i = 0; i < goArray.length; i++) {
            goArray[i] = null;
        }
        goArray = new GameObject[0];
    }
}