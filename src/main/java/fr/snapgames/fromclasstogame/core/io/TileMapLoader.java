package fr.snapgames.fromclasstogame.core.io;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.tilemap.Tile;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileLayer;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileMap;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileSet;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TileMapLoader extends System {
    private static final Logger logger = LoggerFactory.getLogger(TileMapLoader.class);
    /**
     * the default file path to the level files
     */
    private static String defaultLvlFilePath;

    public TileMapLoader(Game g) {
        super(g);
    }

    /**
     * Load tilemap and all its objects from the level file named <code>fileName</code>.
     *
     * @param fileName name of the level file (*.lvl)
     * @return a brand new TileMap fully initialized.
     */
    public TileMap load(Scene scene, String fileName) {
        TileMap tm = new TileMap();
        FileAttributes fa = FileAttributes.read(fileName);
        tm.name = fa.get("title");
        tm.setTileSets(parseTileSet(tm, fa));
        tm.setLayers(parseLayers(tm, fa));
        return tm;
    }

    private static List<TileSet> parseTileSet(TileMap tm, FileAttributes fa) {
        List<TileSet> tileSets = new ArrayList<>();
        List<String> tsList = fa.find("level.tileset");
        tsList.forEach(s -> {
            String tsImage = fa.getSubAttribute(s, "file");
            String[] tsTileSize = fa.getSubAttribute(s, "size").split("x");
            int tw = Integer.parseInt(tsTileSize[0]);
            int th = Integer.parseInt(tsTileSize[1]);
            String listMap = fa.getSubAttribute(s, "list");
            String name = fa.getSubAttribute(s, "name");
            String imageFileName = fa.getSubAttribute(s, "file");
            try {
                BufferedImage img = ResourceManager.getImage(imageFileName);
                TileSet ts = new TileSet(name);
                extractTiles(ts, img, tsList, tw, th);
                tm.addTileSet(ts);
            } catch (UnknownResource e) {
                e.printStackTrace();
            }
        });
        return tileSets;
    }

    private static void extractTiles(
            TileSet ts,
            BufferedImage img,
            List<String> tsList,
            int tw, int th) {
        tsList.forEach(s -> {
            String[] values = s.split("=");
            String[] pos = values[1].split(",");
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);

            Tile t = new Tile(values[0], tw, th, null);
            t.setImage(img.getSubimage(x * tw, y * th, tw, th));
            ts.add(values[0], t);
        });
    }

    private static List<TileLayer> parseLayers(TileMap tm, FileAttributes fa) {
        List<TileLayer> layers = new ArrayList<>();
        List<String> strLayers = fa.find("layer");

        for (String s : strLayers) {

            String name = fa.getSubAttribute(s, "name");
            int priority = Integer.parseInt(fa.getSubAttribute(s, "priority"));

            String backGround = fa.getSubAttribute(s, "image");
            if (backGround != null) {
                BufferedImage bckImg = null;
                try {
                    bckImg = ImageIO.read(FileAttributes.class.getResourceAsStream(backGround));
                    TileLayer tl = new TileLayer(name, 0, 0, priority);
                    tl.setOffset(0, 0);
                    tl.setImage(bckImg);
                    layers.add(tl);
                } catch (IOException e) {
                    logger.error("Unable to read the i<mage " + backGround);
                }
            }

            String layerMap = fa.getSubAttribute(s, "map");
            if (layerMap != null) {
                String[] size = fa.getSubAttribute(s, "size").split("x");
                int width = Integer.parseInt(size[0]);
                int height = Integer.parseInt(size[1]);
                TileLayer tl = new TileLayer(name, width, height, priority);
                tl.setOffset(0, 0);
                String tileSet = fa.getSubAttribute(s, "tileset");
                tl.setTileSet(tileSet);
                tl.setMap(layerMap);
                layers.add(tl);
            }

        }
        return layers;
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
