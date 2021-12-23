package fr.snapgames.fromclasstogame.core.physic;

import java.awt.*;

public class Utils {

    public static double rand(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    public static int rand(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public static Vector2d randV2d(double minX, double maxX, double minY, double maxY) {
        return new Vector2d(Utils.rand(minX, maxX), Utils.rand(minY, maxY));
    }

    public static Vector2d add(Vector2d p1, Vector2d p2) {
        Vector2d r = new Vector2d();
        if (p1 != null & p2 != null) {
            r.x = p1.x + p2.x;
            r.y = p1.y + p2.y;
        }
        return r;
    }

    public static Color randomColor(Color c1, Color c2) {
        int rMax = Math.max(c1.getRed(), c2.getRed());
        int gMax = Math.max(c1.getGreen(), c2.getGreen());
        int bMax = Math.max(c1.getBlue(), c2.getBlue());
        int aMax = Math.max(c1.getAlpha(), c2.getAlpha());

        int rMin = Math.min(c1.getRed(), c2.getRed());
        int gMin = Math.min(c1.getGreen(), c2.getGreen());
        int bMin = Math.min(c1.getBlue(), c2.getBlue());
        int aMin = Math.min(c1.getAlpha(), c2.getAlpha());

        float r = (float) fixThreshold((Math.random() * rMax + rMin), 0.0, 1.0);
        float g = (float) fixThreshold((Math.random() * gMax + gMin), 0.0, 1.0);
        float b = (float) fixThreshold((Math.random() * bMax + bMin), 0.0, 1.0);
        float a = (float) fixThreshold((Math.random() * aMax + aMin), 0.0, 1.0);

        return new Color(r, g, b, a);
    }

    public static double fixThreshold(double c, double min, double max) {
        return Math.max(min, Math.min(c, max));
    }

}
