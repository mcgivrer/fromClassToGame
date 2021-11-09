package fr.snapgames.fromclasstogame.core.physic;

public class Utils {

    public static double rand(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    public static Vector2d randV2d(double minX, double maxX, double minY, double maxY) {
        return new Vector2d(Utils.rand(minX, maxX), Utils.rand(minY, maxY));
    }

    public static Vector2d add(Vector2d p1, Vector2d p2) {
        Vector2d r = new Vector2d();
        r.x = p1.x + p2.x;
        r.y = p1.y + p2.y;
        return r;
    }
}
