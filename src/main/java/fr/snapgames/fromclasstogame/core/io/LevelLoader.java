package fr.snapgames.fromclasstogame.core.io;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.tilemap.Tile;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileLayer;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileMap;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileSet;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.system.System;

/**
 * LevelLoader load a level properties file and convert it to a TileMap object.
 *
 * @author Frédéric Delorme
 * @since 0.0.3
 */
public class LevelLoader extends System {
    private static final Logger logger = LoggerFactory.getLogger(LevelLoader.class);
    /**
     * the default file path to the level files
     */
    private static String defaultLvlFilePath;

    public LevelLoader(Game g) {
        super(g);
    }

    /**
     * Load tilemap and all its objects from the level file named <code>fileName</code>.
     *
     * @param fileName name of the level file (*.lvl)
     * @return a brand new TileMap fully initialized.
     */
    public TileMap loadFrom(String fileName) {
        TileMap tm = new TileMap();
        FileAttributes fa = FileAttributes.read(fileName);
        tm.name = fa.get("name");
        tm.addAttribute("title", fa.get("title"));
        tm.addAttribute("world", fa.get("world"));
        tm.addAttribute("level", fa.get("level"));
        tm.addAttribute("description", fa.get("description"));
        List<TileSet> ts = parseTileSet(fa);
        tm.setTileSets(ts);
        List<TileLayer> tl = parseLayers(fa);
        tm.setLayers(tl);
        return tm;
    }

    private List<TileSet> parseTileSet(FileAttributes fa) {
        List<TileSet> tileSets = new ArrayList<>();
        List<String> tsList = fa.find("tileset");
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

                TileSet ts = extractTiles(name, img, listMap, tw, th);
                tileSets.add(ts);
            } catch (UnknownResource e) {
                logger.error("Unable to read image file from {}", imageFileName, e);
            }
        });
        return tileSets;
    }

    private TileSet extractTiles(String name, BufferedImage img,
                                 String tsList,
                                 int tw, int th) {
        Pattern pnv = Pattern.compile("^(?<key>[a-zA-Z0-9]*)\\((?<value>[a-zA-Z0-9\\.]*)\\)$");

        TileSet ts = new TileSet(name);
        Arrays.asList(
                tsList.substring(1, tsList.length() - 1).split(",")
        ).forEach(s -> {
            String[] values = s.split("/");
            String[] geo = values[1].split("[.]");
            int x = Integer.parseInt(geo[0]);
            int y = Integer.parseInt(geo[1]);
            int w = Integer.parseInt(geo[2]);
            int h = Integer.parseInt(geo[3]);

            Tile t = new Tile(values[0], tw * w, th * h);
            if (values.length > 2) {
                if (values[2].contains(".")) {
                    String[] attrs = values[2].split("\\.");
                    for (String attr : attrs) {
                        Matcher m = pnv.matcher(attr);
                        if (m.matches()) {
                            String key = m.group("key");
                            Object value = m.group("value");
                            value = extractTypedValue(key, value);
                            t.addAttribute(key, value);
                        } else {
                            t.addAttribute(attr, true);
                        }
                    }
                } else {
                    t.addAttribute(values[2], true);
                }
            }
            t.setImage(img.getSubimage(x * tw, y * th, tw * w, th * h));
            ts.add(values[0], t);
        });
        return ts;
    }

    private Object extractTypedValue(String key, Object value) {
        try {
            value = Integer.parseInt((String) value);
        } catch (NumberFormatException nfe1) {
            try {
                value = Integer.parseInt((String) value);
            } catch (NumberFormatException nfe2) {
                try {
                    if ("TRUEFALSEtruefalse".contains((String) value)) {
                        value = Boolean.parseBoolean((String) value);
                    }
                } catch (NumberFormatException nfe3) {
                    logger.error("Unable to read attribute value fo {}:{}", key, value);
                }
            }
        }
        return value;
    }

    private List<TileLayer> parseLayers(FileAttributes fa) {
        List<TileLayer> layers = new ArrayList<>();
        List<String> strLayers = fa.find("layer");

        for (String s : strLayers) {

            String name = fa.getSubAttribute(s, "name");
            int priority = Integer.parseInt(fa.getSubAttribute(s, "priority"));
            String type = fa.getSubAttribute(s, "type");
            switch (type) {
                case "background":
                    String backGround = fa.getSubAttribute(s, "file");
                    if (Optional.ofNullable(backGround).isPresent()) {
                        try {
                            BufferedImage bckImg = ResourceManager.getImage(backGround);
                            TileLayer tl = new TileLayer(name, 0, 0, priority);
                            tl.setOffset(0, 0);
                            tl.setImage(bckImg);
                            layers.add(tl);
                        } catch (UnknownResource e) {
                            logger.error("Unable to read the background image '{}'", backGround, e);
                        }
                    } else {
                        logger.error("Unable to read background: file is not defined");
                    }
                    break;
                case "map":
                    String layerMap = fa.getSubAttribute(s, "rows");
                    if (Optional.ofNullable(layerMap).isPresent()) {
                        String[] size = fa.getSubAttribute(s, "size").split("x");
                        int width = Integer.parseInt(size[0]);
                        int height = Integer.parseInt(size[1]);
                        TileLayer tl = new TileLayer(name, width, height, priority);
                        tl.setOffset(0, 0);
                        String tileSet = fa.getSubAttribute(s, "tileset");
                        tl.setTileSet(tileSet);
                        tl.setMap(layerMap);
                        layers.add(tl);
                    } else {
                        logger.error("Unable to set map layer: rows are not defined");

                    }
                    break;
                default:
                    break;
            }


        }
        return layers;
    }

    @Override
    public String getName() {
        return LevelLoader.class.getName();
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
