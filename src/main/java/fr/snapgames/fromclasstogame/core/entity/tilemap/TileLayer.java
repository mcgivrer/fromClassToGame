package fr.snapgames.fromclasstogame.core.entity.tilemap;

public class TileLayer {
    private double offsetX, offsetY;
    private int width, height;
    private String name;
    private Character[] map;
    private int priority;

    public TileLayer(String name, int width, int height, int priority) {
        this.name = name;
        this.width = width;
        this.height = height;
        map = new Character[width * height];
        this.priority = priority;
    }

    public TileLayer setOffset(double x, double y) {
        this.offsetX = x;
        this.offsetY = y;
        return this;
    }
}
