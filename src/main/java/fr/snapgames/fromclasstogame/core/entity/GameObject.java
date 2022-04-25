package fr.snapgames.fromclasstogame.core.entity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
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
     * THe Type of Object to be rendered
     */
    public enum GOType {
        POINT, RECTANGLE, CIRCLE, IMAGE, ANIMATION, OTHER
    }

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
    public int layer;
    public int priority;
    public boolean relativeToCamera;
    public GOType objectType = GOType.RECTANGLE;
    public Color color;
    // define animation (if not null)
    Animation animation;
    public BufferedImage image;

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
    protected Map<String, Object> attributes = new HashMap<>();
    /**
     * Child objects.
     */
    protected List<GameObject> child = new ArrayList<>();

    /**
     * true if this object collides with something.
     */
    private boolean collision;

    /**
     * an internal identification code for this object (mainly used in tilemap)
     */
    private String code;

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

    /**
     * Define the rendering priority.
     *
     * @param priority
     * @return
     */
    public GameObject setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Define the layer for this GameObject
     *
     * @param layer the layer number (0 to n where 0 is in front of camera)
     * @return The updated GameObject.
     */
    public GameObject setLayer(int layer) {
        this.layer = layer;
        return this;
    }

    /**
     * Define if this Object must stick to the Camera viewport.
     *
     * @param rtc if true, this object will be stick to Camera viewport.
     * @return the updated GameObject.
     */
    public GameObject setRelativeToCamera(boolean rtc) {
        this.relativeToCamera = rtc;
        return this;
    }

    /**
     * Define the mass for this GameObject.
     *
     * @param mass mass in Kg for this GameObject.
     * @return the updated GameObject.
     */
    public GameObject setMass(double mass) {
        this.mass = mass;
        return this;
    }

    /**
     * Define the Material for this GameObject.
     *
     * @param mat
     * @return
     */
    public GameObject setMaterial(Material mat) {
        this.material = mat;
        return this;
    }


    /**
     * Add a behavior to this GameObject.
     *
     * @param b the Behavior implementation to be added.
     * @return the updated GameObject.
     */
    public GameObject add(Behavior<GameObject> b) {
        if (!behaviors.contains(b)) {
            behaviors.add(b);
        }
        return this;
    }

    /**
     * Add an attribute attrName with value attrValue.
     *
     * @param attrName  the name for this attribute.
     * @param attrValue the value of this attribute.
     * @return
     */
    public GameObject addAttribute(String attrName, Object attrValue) {
        this.attributes.put(attrName, attrValue);
        return this;
    }

    /**
     * Retrieve an attribute name, or return the defaultValue.
     *
     * @param name         the name of the attribute to be retrieved.
     * @param defaultValue the default value for this attribute.
     * @return
     */
    public Object getAttribute(String name, Object defaultValue) {
        return attributes.getOrDefault(name, defaultValue);
    }

    /**
     * Retrieve all the attributes for this GameObject.
     *
     * @return a Map of String,Object of attributes.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Define the GameObject life duration in ms.
     *
     * @param ms the life duration in milliseconds
     * @return the updated GameObject.
     */
    public GameObject setDuration(int ms) {
        this.lifeStart = ms;
        this.life = ms;
        return this;
    }

    /**
     * Define a specific Gravity for this GameObject.
     *
     * @param gravity the Vector2d corresponding to the applied Gravity for this GameObject
     * @return the updated GameObject.
     */
    public GameObject setGravity(Vector2d gravity) {
        this.gravity = gravity;
        return this;
    }

    public GameObject addChild(GameObject ps) {
        child.add(ps);
        return this;
    }


    /**
     * Retrieve child object for this GameObject.
     *
     * @return the list of child GameObject
     */
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

    /**
     * Retrieve the current animation
     *
     * @return the current {@link Animation} object.
     */
    public Animation getAnimation() {
        return this.animation;
    }

    /**
     * Define the Physic type for this object
     *
     * @param type
     * @return
     * @see PEType
     */
    public GameObject setPhysicType(PEType type) {
        this.physicType = type;
        return this;
    }


    public GameObject setCode(String code) {
        this.code = code;
        return this;
    }
}
