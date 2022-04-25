package fr.snapgames.fromclasstogame.core.entity.tilemap;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileMap extends GameObject {
    private List<TileSet> tileSets;
    private List<TileLayer> layers;

    /**
     * List of object loaded and maintained by/from the TileMap.
     */
    private Map<String, GameObject> mapObjects = new HashMap<>();

    /**
     * Create a default new TileMap with no name and  initial position at (0.0, 0.0).
     */
    public TileMap() {
        super("", new Vector2d(0.0, 0.0));
    }

    /**
     * Create a new TileMap like any other GameObject with a name and its initial position.
     *
     * @param name Name of the object.
     */
    public TileMap(String name) {
        super(name, new Vector2d(0.0, 0.0));
    }

    public List<TileSet> getTileSets() {
        return tileSets;
    }

    public List<TileLayer> getTileLayers() {
        return layers;
    }

    public void setTileSets(List<TileSet> tileSets) {
        this.tileSets = tileSets;
    }

    public void setLayers(List<TileLayer> parseLayers) {
        layers = parseLayers;
    }

    public void addLayer(TileLayer layer) {
        layers.add(layer);
    }

    public void addTileSet(TileSet ts) {
        tileSets.add(ts);
    }

    public TileMap addMapObject(GameObject go) {
        mapObjects.put(go.name, go);
        addChild(go);
        return this;
    }

    /**
     * Retrieve an object loaded in the tilemap.
     *
     * @param name
     * @return
     */
    public GameObject getMapObject(String name) throws UnkownGameObjectException {
        if (mapObjects.containsKey(name)) {
            return mapObjects.get(name);
        } else {
            throw new UnkownGameObjectException(name);
        }
    }

    public void addObjectList(List<GameObject> ol) {
        ol.stream().forEach(o -> addMapObject(o));
    }
}

