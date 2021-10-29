package fr.snapgames.fromclasstogame.core.entity.tilemap;

import fr.snapgames.fromclasstogame.core.entity.GameObject;

import java.util.List;

public class TileMap extends GameObject {
    private List<TileSet> tileSets;
    private List<TileLayer> layers;

    public TileMap(String name, double x, double y) {
        super(name, x, y);
    }
}
