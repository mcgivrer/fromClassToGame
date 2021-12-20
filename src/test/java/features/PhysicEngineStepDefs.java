package features;

import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.PhysicEngine;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import io.cucumber.java8.En;

import java.util.List;

import static org.junit.Assert.*;

public class PhysicEngineStepDefs extends CommonDefSteps implements En {

    Configuration config;
    PhysicEngine pe;

    public PhysicEngineStepDefs() {

        Given("the Configuration is loaded from {string}",
                (String cfgFile) -> config = new Configuration(cfgFile));


        When("the PhysicEngine is created", () -> {
            pe = new PhysicEngine(null);
            pe.initialize(config);
        });

        Then("the PhysicEngine default world width is {double}",
                (Double width) -> assertEquals("The world width is not set to " + width, width, pe.getWorld().width, 0.0));

        Then("the PhysicEngine default world height is {double}",
                (Double height) -> assertEquals("The world height is not set to " + height, height, pe.getWorld().height, 0.0));

        And("the PhysicEngine default world gravity is {double},{double}",
                (Double vx, Double vy) -> {
                    Vector2d vg = new Vector2d(vx, vy);
                    assertEquals("The world gravity is not set to " + vg, vg, pe.getWorld().gravity);
                });

        Then("the PhysicEngine has no object", () -> {
            assertTrue("The list of object in the PhysicEngine is not empty", pe.getObjects().isEmpty());
        });

        And("the PhysicEngine is initialized", () -> {
            pe = game.getPhysicEngine();
            assertNotNull("The PhysicEngine has not been initialized", pe);
        });

        Then("the PhysicEngine has {int} objects", (Integer nbObjectInPE) -> {
            PhysicEngine pe2 = (PhysicEngine) SystemManager.get(PhysicEngine.class);
            List<GameObject> objs = pe2.getObjects();
            assertEquals("The Number of object managed by the PhysicEngine is not reached", nbObjectInPE, Integer.valueOf(objs.size()));
        });
    }
}
