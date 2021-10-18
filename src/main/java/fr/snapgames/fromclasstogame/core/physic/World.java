package fr.snapgames.fromclasstogame.core.physic;

public class World {

    public double width;
    public double height;
    public double maxVelocity = 8.0;

    public Vector2d gravity = new Vector2d(0, -0.981);

    public World(double width, double height) {
        this.width = width;
        this.height = height;
    }


    public World setGravity(Vector2d g) {
        this.gravity = g;
        return this;
    }
}
