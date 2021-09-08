package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.physic.Material;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GameObject implements Entity {


    public enum GOType {
        POINT, RECTANGLE, CIRCLE, IMAGE, OTHER
    }

    private static int index = 0;

    public int id = ++index;
    public String name = "noname_" + id;

    public double x;
    public double y;
    public double dx;
    public double dy;

    public double width;
    public double height;

    public BoundingBox bbox;

    public GOType type = GOType.RECTANGLE;

    public Color color;
    public BufferedImage image;

    public int layer;
    public boolean relativeToCamera;
    public int priority;

    public double gravity = 0;

    public Material material;
    public double mass = 1;

    public GameObject(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = Color.GREEN;
        this.priority = 0;
        this.layer = 1;
        this.relativeToCamera = false;
    }

    public void update(long dt) {
    }

    public GameObject setType(GOType type) {
        this.type = type;
        return this;
    }

    public GameObject setColor(Color c) {
        this.color = c;
        return this;
    }

    public GameObject setSpeed(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        return this;
    }

    public GameObject setSize(double w, double h) {
        this.width = w;
        this.height = h;
        return this;
    }

    public GameObject setImage(BufferedImage image) {
        this.image = image;
        setSize(image.getWidth(), image.getHeight());
        return this;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public GameObject setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public GameObject setLayer(int layer) {
        this.layer = layer;
        return this;
    }

    public GameObject relativeToCamera(boolean rtc) {
        this.relativeToCamera = rtc;
        return this;
    }

    public GameObject setMaterial(Material mat) {
        this.material = mat;
        return this;
    }

}
