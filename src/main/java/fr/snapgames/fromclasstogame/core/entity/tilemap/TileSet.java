package fr.snapgames.fromclasstogame.core.entity.tilemap;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TileSet {
    private BufferedImage image;
    private Map<String, BufferedImage> tiles = new HashMap<>();
    private String name;

    public TileSet(String name, BufferedImage img, int tileWidth, int tileHeight) {
        this.name = name;
        this.image = img;
        int idx = 0;
        for (int ix = 0; ix < img.getWidth(); ix += tileWidth) {
            for (int iy = 0; iy < img.getHeight(); iy += tileHeight) {
                tiles.put("t" + (idx++), img.getSubimage(ix, iy, tileWidth, tileHeight));
            }
        }
    }
}
