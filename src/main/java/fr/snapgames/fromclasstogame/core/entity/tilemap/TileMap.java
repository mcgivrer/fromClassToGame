package fr.snapgames.fromclasstogame.core.entity.tilemap;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.util.List;

public class TileMap extends GameObject {
    private List<TileSet> tileSets;
    private List<TileLayer> layers;

    public TileMap(String name, Vector2d pos) {
        super(name, pos);
    }

    public List<TileSet> getTileSets() {
        return tileSets;
    }

    public List<TileLayer> getTileLayers() {
        return layers;
    }

    public void setTileSets(List<TileSet> parseTileSet) {
        tileSets = parseTileSet;
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
}
