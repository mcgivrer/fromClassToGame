package fr.snapgames.fromclasstogame.core.entity.tilemap;

import java.util.HashMap;
import java.util.Map;

public class TileSet {

    private String name;
    private Map<String, Tile> tiles = new HashMap<>();

    public TileSet(String name) {
        this.name = name;

    }

    public void add(String k, Tile t) {
        tiles.put(k, t);
    }


    public Map<String, Tile> getTiles() {
        return tiles;
    }
}
