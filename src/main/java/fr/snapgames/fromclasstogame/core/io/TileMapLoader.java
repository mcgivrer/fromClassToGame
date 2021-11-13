package fr.snapgames.fromclasstogame.core.io;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileMap;
import fr.snapgames.fromclasstogame.core.system.System;

public class TileMapLoader extends System {

    /**
     * the default file path to the level files
     */
    private static  String defaultLvlFilePath;

    public TileMapLoader(Game g) {
        super(g);
    }

    public static TileMap load(String fileName) {
        TileMap tm = null;


        return tm;
    }

    @Override
    public String getName() {
        return TileMapLoader.class.getName();
    }

    @Override
    public int initialize(Configuration config) {
        defaultLvlFilePath = config.levelPath;
        return 0;
    }

    @Override
    public void dispose() {

    }


}
