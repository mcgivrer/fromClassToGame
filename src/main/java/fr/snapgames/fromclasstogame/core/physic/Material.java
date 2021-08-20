package fr.snapgames.fromclasstogame.core.physic;

import java.util.HashMap;
import java.util.Map;

public class Material {
    // TODO edit the factor for this default materials
    public enum DefaultMaterial {
        WOOD(new Material("wood", 0.3, 0.69, 0.69)),
        METAL(new Material("metal", 0.1, 1, 1)),
        GLASS(new Material("glass", 0.0, 1, 1)),
        ICE(new Material("ice", 0.1, 0.1, 1)),
        RUBBER(new Material("rubber", 0.98, 1, 1)),
        WATER(new Material("water", 0.02, 1, 1)),
        AIR(new Material("air", 1, 1, 1));
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

    public Material(String name, double b, double df, double sf) {
        this.name = name;
        this.bouncyness=b;
        this.dynFriction=df;
        this.staticFriction=sf;
    }

    public double bouncyness;
    public double dynFriction;
    public double staticFriction;
}
