package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractEntity<T> implements Entity<T> {

    private static int index = 0;
    public int id = index++;

    public Color color;

    public String name = "entity_" + id;


    protected boolean active = true;

    /**
     * Geometric attributes
     */
    public Vector2d position = new Vector2d();
    public double width;
    public double height;
    public BoundingBox bbox = new BoundingBox();

    /**
     * Child objects.
     */
    protected java.util.List<GameObject> child = new ArrayList<>();

    /**
     * Debug level to activate the debug display output for this object.
     */
    public int debugLevel = 0;
    public Color debugFillColor = new Color(0.8f, 0.4f, 0.2f, 0.25f);
    public Color debugLineColor = Color.RED;
    public int debugOffsetX = -40;
    public int debugOffsetY = 10;
    protected Color debugColor;

    public AbstractEntity() {
        id = index++;
        name = "entity_" + id;
    }

    public AbstractEntity(String name) {
        this();
        this.name = name;
    }

    public AbstractEntity(String name, Vector2d position) {
        this(name);
        this.position = position;
    }


    /**
     * Retrieve the internal index of the GameObject counter.
     *
     * @return the internal GameObject indexer value
     */
    public static int getIndex() {
        return index;
    }

    public boolean isActive() {
        return active;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public T setPosition(Vector2d pos) {
        this.position = pos;
        return (T) this;
    }

    public T setPosition(double x, double y) {
        this.position.x = x;
        this.position.y = y;
        return (T) this;
    }

    public T setActive(boolean active) {
        this.active = active;
        return (T) this;
    }

    public int getDebug() {
        return this.debugLevel;
    }

    public T setDebug(int d) {
        this.debugLevel = d;
        return (T) this;
    }

    public T setDebugOffset(int dox, int doy) {
        this.debugOffsetX = dox;
        this.debugOffsetY = doy;
        return (T) this;
    }

    public Color getDebugColor() {
        return this.debugColor;
    }

    public T setDebugColor(Color color) {
        this.debugColor = color;
        return (T) this;
    }


    public T setSize(double w, double h) {
        this.width = w;
        this.height = h;
        return (T) this;
    }

    public String getName() {
        return name;
    }


    public List<GameObject> getChild() {
        return child;
    }

    public T addChild(GameObject childGo) {
        child.add(childGo);
        return (T) this;
    }

    public T setColor(Color c) {
        this.color = c;
        return (T) this;
    }

}
