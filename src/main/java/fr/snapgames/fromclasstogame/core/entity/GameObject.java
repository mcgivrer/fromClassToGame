package fr.snapgames.fromclasstogame.core.entity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.physic.Material;
import fr.snapgames.fromclasstogame.core.physic.PEType;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

/**
 * <p><code>GameObject</code> is the object managed by all the game systems.</p>
 * <p>It's mainly any object displayed in the game !</p>
 * <p>It will support many rendering shape as {@link GOType}.</p>
 * <p>It will be rendered through the {@link fr.snapgames.fromclasstogame.core.gfx.renderer.GameObjectRenderHelper}</p>.
 *
 * @author Frédéric Delorme.
 * @see fr.snapgames.fromclasstogame.core.gfx.renderer.GameObjectRenderHelper
 * @since 0.0.1
 */
public class GameObject extends AbstractEntity<GameObject> {

    /**
     * Geometric attributes
     */
    public double width;
    public double height;
    public BoundingBox box = new BoundingBox();

    /**
     * Physic and mechanic attributes
     */
    public Vector2d velocity = new Vector2d();
    public Vector2d acceleration = new Vector2d();
    public List<Vector2d> forces = new ArrayList<>();
    public Material material;
    public double mass = 1;
    public Vector2d gravity = new Vector2d();
    public PEType physicType = PEType.DYNAMIC;
    /**
     * Rendering attributes
     */
    public GOType objectType = GOType.RECTANGLE;
    public Color color;
    public BufferedImage image;
    // define animation (if not null)
    Animation animations;
    public int layer;
    public int priority;
    public boolean relativeToCamera;
    /**
     * life duration of this object (default is -1 = infinite).
     */
    public long life = -1;
    public long lifeStart = -1;
    public boolean alive = true;
    /**
     * List of behaviors to be applied on this GameObject
     */
    public List<Behavior<GameObject>> behaviors = new ArrayList<>();
    public boolean rendered;
    protected Map<String, Object> attributes = new HashMap<>();
    /**
     * Child objects.
     */
    protected List<GameObject> child = new ArrayList<>();
    private boolean active = true;
    /**
     * debugging data
     */
    private List<String> debugData = new ArrayList<>();

    /**
     * true if this object collides with something.
     */
    private boolean collision;

    /**
     * Initialize a GameObject with a default generated name "noname_999"
     * where 999 is the current internal GameObject index value.
     */
    public GameObject() {
        super();
    }

    /**
     * Create the new Game object with an `objectName`.
     *
     * @param objectName name of this new object
     */
    public GameObject(String objectName) {
        super(objectName);
        this.physicType = PEType.DYNAMIC;
    }

    /**
     * Create a GameObject with an `objectName` at a specific `position`.
     *
     * @param objectName name of this new object
     * @param position   position at initialization of this object.
     */
    public GameObject(String objectName, Vector2d position) {
        super(objectName, position);
        this.active = true;
        setDebugColor(Color.YELLOW);
        life = -1;
        lifeStart = -1;
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

    /**
     * Update internal life attribute according to elapsed time since previous cal cycle.
     *
     * @param dt elapsed time since previous call.
     */
    public void update(long dt) {
        box.update(this);
        if (life > -1) {
            if (life - dt >= 0) {
                life -= dt;
            } else {
                active = false;
                life = -1;
            }
        }
    }

    /**
     * Build the Debugging information for this object.
     *
     * @return a list of String for debugging information display.
     */
    public List<String> getDebugInfo() {
        this.debugOffsetX = -40;
        this.debugOffsetY = 10;
        List<String> debugInfo = new ArrayList<>();
        if (debugLevel > 0) {
            debugInfo.add("n:" + name);
            debugInfo.add("dbgLvl:" + debugLevel);
            if (debugLevel > 2) {
                debugInfo.add("pos:" + position.toString());
                debugInfo.add("vel:" + velocity.toString());
                debugInfo.add("acc:" + acceleration.toString());
                debugInfo.add("life:" + life);
                if (debugLevel > 3) {
                    debugInfo.add("mass:" + mass);
                    if (material != null) {
                        debugInfo.add("mat:" + material.name);
                        debugInfo.add("frict:" + material.dynFriction);
                    }
                    debugInfo.add("contact:" + getAttribute("touching", false));
                    debugInfo.add("jumping:" + getAttribute("jumping", false));
                }
            }
            debugInfo.add("active:" + (active ? "on" : "off"));
        }
        return debugInfo;
    }

    public GameObject setObjectType(GOType objectType) {
        this.objectType = objectType;
        return this;
    }

    public GameObject setColor(Color c) {
        this.color = c;
        return this;
    }

    public GameObject setSize(double w, double h) {
        this.width = w;
        this.height = h;
        box.update(this);
        return this;
    }

    public GameObject setImage(BufferedImage image) {
        this.image = image;
        setSize(image.getWidth(), image.getHeight());
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

    public GameObject setRelativeToCamera(boolean rtc) {
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


    public GameObject add(Behavior<GameObject> b) {
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
        return attributes.getOrDefault(name, defaultValue);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public GameObject setDuration(int ms) {
        this.lifeStart = ms;
        this.life = ms;
        return this;
    }

    public GameObject setGravity(Vector2d gravity) {
        this.gravity = gravity;
        return this;
    }


    public List<GameObject> getChild() {
        return child;
    }

    public GameObject setCollide(boolean collisionFlag) {
        this.collision = collisionFlag;
        return this;
    }


    public GameObject setBoundingBox(BoundingBox boundingBox) {
        this.box = boundingBox;
        return this;
    }

    public enum GOType {
        POINT, RECTANGLE, CIRCLE, IMAGE, OTHER
    }

    public Animation getAnimations() {
        return this.animations;
    }
}
