package fr.snapgames.fromclasstogame;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GameObject {

    private static int index = 0;

    public int id = ++index;
    public String name = "noname_" + id;

    public double x;
    public double y;
    public double dx;
    public double dy;

    public double width;
    public double height;

    public Color color;
    public BufferedImage image;
    public int priority;

    public double gravity = 0;

    public GameObject(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = Color.GREEN;
        this.priority = 0;
    }

    public void update(long dt) {
        x += dx * dt;
        y += (dy + gravity) * dt;
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
        return this;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

}
