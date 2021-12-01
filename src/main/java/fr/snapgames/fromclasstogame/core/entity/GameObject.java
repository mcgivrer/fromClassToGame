package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.physic.Material;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameObject implements Entity {


    private static int index = 0;
    public int id = ++index;
    public String name = "noname_" + id;

    public boolean active = true;

    public Vector2d position = new Vector2d();
    public Vector2d velocity = new Vector2d();
    public Vector2d acceleration = new Vector2d();

    public List<Vector2d> forces = new ArrayList<>();

    public Material material;
    public double mass = 1;
    public Vector2d gravity = new Vector2d();

    public double width;
    public double height;
    public BoundingBox bbox = new BoundingBox();
    public GOType type = GOType.RECTANGLE;
    public Color color;
    public BufferedImage image;
    public int layer;
    public boolean relativeToCamera;
    public int priority;
    public int life = -1;
    public List<Behavior<GameObject>> behaviors = new ArrayList<>();
    public int debugOffsetX;
    public int debugOffsetY;
    protected Map<String, Object> attributes = new HashMap<>();
    private List<String> debugData = new ArrayList<>();
    private int debug;

    public GameObject(String objectName) {
        this.name = objectName;
    }

    public GameObject(String objectName, Vector2d position) {
        this.name = objectName;
        setPosition(position);
    }

    @Deprecated
    public GameObject(String name, double x, double y) {
        this(name);
        setPosition(x, y);
        this.color = Color.GREEN;
        this.priority = 0;
        this.layer = 1;
        this.relativeToCamera = false;
    }

    public static int getIndex() {
        return index;
    }

    public void update(long dt) {
        bbox.update(this);
        if (life > -1) {
            if (life - dt >= 0) {
                life -= dt;
            } else {
                active = false;
                life = -1;
            }
        }
    }

    public List<String> getDebugInfo() {
        this.debugOffsetX = 20;
        this.debugOffsetY = 20;
        List<String> debugInfo = new ArrayList<>();
        debugInfo.add("n:" + name);
        debugInfo.add("pos:" + position.toString());
        debugInfo.add("vel:" + velocity.toString());
        debugInfo.add("acc:" + acceleration.toString());
        debugInfo.add("mass:" + mass);
        if (material != null) {
            debugInfo.add("mat:" + material.name);
            debugInfo.add("frict:" + material.dynFriction);
        }
        debugInfo.add("contact:" + getAttribute("touching", false));
        debugInfo.add("jumping:" + getAttribute("jumping", false));
        debugInfo.add("active:" + (active ? "on" : "off"));
        return debugInfo;
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

    public int getDebug() {
        return this.debug;
    }

    public GameObject setDebug(int d) {
        this.debug = d;
        return this;
    }

    public GameObject add(Behavior b) {
        if (!behaviors.contains(b)) {
            behaviors.add(b);
        }
        return this;
    }

    public GameObject addAttribute(String attrName, Object attrValue) {
        this.attributes.put(attrName, attrValue);
        return this;
    }

    public Object getAttribute(String name, Object defaultValue) {
        if (attributes.containsKey(name)) {
            return attributes.get(name);
        } else {
            return defaultValue;
        }
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public GameObject setDuration(int ms) {
        this.life = ms;
        return this;
    }

    public GameObject setGravity(Vector2d gravity) {
        this.gravity = gravity;
        return this;
    }

    public enum GOType {
        POINT, RECTANGLE, CIRCLE, IMAGE, OTHER
    }

}
