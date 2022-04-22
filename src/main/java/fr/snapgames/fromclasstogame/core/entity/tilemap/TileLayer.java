package fr.snapgames.fromclasstogame.core.entity.tilemap;

import java.awt.image.BufferedImage;

public class TileLayer {
    private double offsetX, offsetY;
    private int width, height;
    private String name;
    private String map;
    private int priority;
    private BufferedImage image;
    private String tileSetKey;

    public TileLayer(String name, int width, int height, int priority) {
        name = name;
        width = width;
        height = height;
        map = "";
        priority = priority;
    }

    public TileLayer setOffset(double x, double y) {
        this.offsetX = x;
        this.offsetY = y;
        return this;
    }

    public void setImage(BufferedImage bckImg) {
        this.image = bckImg;
    }

    public void setTileSet(String tileSet) {
        this.tileSetKey = tileSet;
    }

    public void setMap(String layerMap) {
        this.map = layerMap;
    }
}
