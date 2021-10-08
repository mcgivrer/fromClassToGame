package fr.snapgames.fromclasstogame.core.physic;

public class World {

    public double width;
    public double height;

    public double gravity = 0.981;

    public World(double width, double height) {
        this.width = width;
        this.height = height;
    }


    public World setGravity(double g) {
        this.gravity = g;
        return this;
    }
}
