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
}
