package features;

import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.physic.PhysicEngine;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import io.cucumber.java8.En;
import org.junit.Assert;

public class PhysicEngineStepDefs implements En {

    Configuration config;
    PhysicEngine pe;

    public PhysicEngineStepDefs() {

        Given("the Configuration is loaded from {string}", (String cfgFile) -> {
            config = new Configuration(cfgFile);
        });
        When("the PhysicEngine is created", () -> {
            pe = new PhysicEngine(null);
            pe.initialize(config);
        });
        Then("the PhysicEngine default world width is {double}", (Double width) -> {
            Assert.assertEquals("The world width is not set to " + width, width, pe.getWorld().width, 0.0);
        });
        Then("the PhysicEngine default world height is {double}", (Double height) -> {
            Assert.assertEquals("The world height is not set to " + height, height, pe.getWorld().height, 0.0);
        });
        And("the PhysicEngine default world gravity is {double},{double}", (Double vx, Double vy) -> {
            Vector2d vg = new Vector2d(vx, vy);
            Assert.assertEquals("The world gravity is not set to " + vg.toString(), vg, pe.getWorld().gravity);
        });

        /*
                    Vector2d vg = new Vector2d(vx, vy);
            Assert.assertEquals("The world gravity is not set to " + vg.toString(), vg, pe.getWorld().gravity);

         */
    }

}
