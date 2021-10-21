package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.physic.Material;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameObject implements Entity {


    public enum GOType {
        POINT, RECTANGLE, CIRCLE, IMAGE, OTHER
    }

    private static int index = 0;

    public int id = ++index;
    public String name = "noname_" + id;

    public Vector2d position = new Vector2d();
    public Vector2d velocity = new Vector2d();
    public Vector2d acceleration = new Vector2d();

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

    protected Map<String, Object> attributes = new HashMap<>();

    public List<Behavior> behaviors = new ArrayList<>();

    public GameObject(String name, double x, double y) {
        this.name = name;
        setPosition(x, y);
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

    public GameObject setPosition(double x, double y) {
        this.position.x = x;
        this.position.y = y;
        return this;
    }

    public GameObject setPosition(Vector2d position) {
        this.position = position;
        return this;
    }

    public GameObject setVelocity(Vector2d velocity) {
        this.velocity = velocity;
        return this;
    }

    public GameObject setAcceleration(Vector2d acceleration) {
        this.acceleration = acceleration;
        return this;
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

    public GameObject setMass(double mass) {
        this.mass = mass;
        return this;
    }

    public GameObject add(Behavior b) {
        assert (behaviors.contains(b));
        behaviors.add(b);
        return this;
    }

    public GameObject addAttribute(String attrName, Object attrValue) {
        this.attributes.put(attrName, attrValue);
        return this;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
