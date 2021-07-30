package fr.snapgames.fromclasstogame;

public class GameObject {

    private static int index = 0;

    public int id;
    public String name = "noname_" + id;

    public double x;
    public double y;
    public double dx;
    public double dy;

    public double width;
    public double height;

    public GameObject(String name, double x, double y) {
        this.name = name;
        this.id = index++;
        this.x = x;
        this.y = y;
    }

    public void update(long dt) {
        x += dx * dt;
        y += dy * dt;
    }

}
