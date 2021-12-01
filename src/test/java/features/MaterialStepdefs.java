package features;

import fr.snapgames.fromclasstogame.core.physic.Material;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import static org.junit.Assert.assertEquals;

public class MaterialStepdefs {
    Material mat;

    @Given("A Material of type {string}")
    public void aMaterialOfType(String materialTypeName) {
        mat = Material.DefaultMaterial.valueOf(materialTypeName).getMaterial();
    }

    @And("the Material has a bounciness of {double}")
    public void theMaterialHasABouncinessOf(Double bouncinessValue) {
        assertEquals("Bounciness for material has not the right value", bouncinessValue, mat.bounciness, 0.0);
    }

    @And("the Material has a density of {double}")
    public void theMaterialHasADensityOf(Double densityValue) {
        assertEquals("Density for material has not the right value", densityValue, mat.density, 0.0);
    }

    @And("the Material has a dynamic friction of {double}")
    public void theMaterialHasADynFrictionOf(Double dynFrictionValue) {
        assertEquals("Dynamic Friction for material has not the right value", dynFrictionValue, mat.dynFriction, 0.0);
    }

    @And("the Material has a static friction of {double}")
    public void theMaterialHasAStaticFrictionOf(Double staFrictionValue) {
        assertEquals("Static Friction for material has not the right value", staFrictionValue, mat.staticFriction, 0.0);
    }
}
