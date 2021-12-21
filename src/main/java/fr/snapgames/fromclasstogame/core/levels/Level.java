package fr.snapgames.fromclasstogame.core.levels;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileSet;

/**
 * Level is a special GameObject having all the Level design and the entity of this level as child.
 */
public class Level extends GameObject {

    private TileSet tilset;

    public Level(String name) {
        super(name);
    }

    public String getName() {
        return this.name;
    }
}
