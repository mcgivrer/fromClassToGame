package fr.snapgames.fromclasstogame.core.physic;

import java.util.HashMap;
import java.util.Map;

/**
 * Rock       Density : 0.6  Restitution : 0.1
 * Wood       Density : 0.3  Restitution : 0.2
 * Metal      Density : 1.2  Restitution : 0.05
 * BouncyBall Density : 0.3  Restitution : 0.8
 * SuperBall  Density : 0.3  Restitution : 0.95
 * Pillow     Density : 0.1  Restitution : 0.2
 * Static     Density : 0.0  Restitution : 0.0
 */
public class Material {
    
    public enum DefaultMaterial {
        ROCK(new Material("rock", 0.6, 1, 1, 1)),
        WOOD(new Material("wood", 0.1, 0.69, 0.69, 0.3)),
        METAL(new Material("metal", 0.05, 1, 1, 1.2)),
        RUBBER(new Material("rubber", 0.8, 1, 1, 0.3)),
        GLASS(new Material("glass", 0.4, 1, 1, 1)),
        ICE(new Material("ice", 0.1, 0.1, 1, 1)),
        AIR(new Material("air", 1, 1, 1, 1)),
        STATIC(new Material("static", 0, 0, 0, 0)),
        NEUTRAL(new Material("neutral", 1, 1, 1, 1));

        private Material material;

        DefaultMaterial(Material m) {
            this.material = m;
        }

        public Material getMaterial() {
            return this.material;
        }
    }

    public static final Map<String, Material> materials = new HashMap<>();

    private final String name;

    public double bounciness;
    public double dynFriction;
    public double staticFriction;
    public double density;

    public Material(String name, double b, double df, double sf, double d) {
        this.name = name;
        this.bounciness = b;
        this.dynFriction = df;
        this.staticFriction = sf;
        this.density = d;
    }


}
