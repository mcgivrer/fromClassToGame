package fr.snapgames.fromclasstogame.core.levels;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class LevelLoader {

    public LevelLoader() {


    }

    /**
     * Load the Level and its parameters and entities from the *.level fileName.
     *
     * @param fileName the name if the level file to be loaded.
     * @return a new Level object initialized from the file *.level.
     */
    public Level loadFrom(String fileName) {
        Level level = loadFile(fileName);
        return level;
    }

    private Level loadFile(String fileName) {

        return null;
    }

    private Level parseLevel(LevelFile gson) {
        Level level = new Level(gson.name);
        return level;
    }
}
