package fr.snapgames.fromclasstogame.core.entity.tilemap;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Tile {
    private String imageId;
    private int width, height;
    private Map<String, Object> attributes = new HashMap<>();
    private BufferedImage image;

    public Tile(String imgId, int w, int h) {
        this(imgId, w, h, null);
    }

    public Tile(String imgId, int w, int h, Map<String, Object> attrs) {
        imageId = imgId;
        width = w;
        height = h;
        attributes = attrs;
    }

    public void addAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setImage(BufferedImage subimage) {
        image = subimage;
    }
}
