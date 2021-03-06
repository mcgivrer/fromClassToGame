package features;

import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import io.cucumber.java8.En;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class ConfigurationStepDefs implements En {
    private Configuration config;

    public ConfigurationStepDefs() {
        Given("the Configuration object is initialized with {string}", (String configFilename) -> {
            config = new Configuration(configFilename);
            config.parseArgs(new String[]{});
        });
        Then("the properties are loaded", () -> {
            assertNotNull("properties have not been loaded", config.defaultValues);
        });
        And("the default title is {string}", (String title) -> {
            assertEquals("the title has not been set to the correct value ", title, config.title);
        });
        And("the default game width is {int}", (Integer width) -> {
            assertEquals("the default width has not been set to " + width, width, config.width, 0.1);
        });
        And("the default game height is {int}", (Integer height) -> {
            assertEquals("the default height has not been set to " + height, height, config.height, 0.1);
        });
        And("the default game scale is {int}", (Integer scale) -> {
            assertEquals("the default scale has not been set to " + scale, scale, config.scale, 0.1);
        });
        And("the default screen is {int}", (Integer screenId) -> {
            assertEquals("the default scale has not been set to " + screenId, screenId, config.defaultScreen, 0.1);
        });
        And("the default world gravity is {double},{double}", (Double gravityX, Double gravityY) -> {
            Vector2d vG = new Vector2d(gravityX, gravityY);
            assertEquals("the default gravity has not been set to (" + gravityX + "," + gravityY + ")", vG, config.gravity);
        });
        And("the scene {string} is {string}", (String name, String className) -> {
            String[] scenes = config.scenes.split(",");
            Map<String, String> mapScenes = new HashMap<>();
            Arrays.asList(scenes).forEach(s -> {
                String[] values = s.split(":");
                mapScenes.put(values[0], values[1]);
            });

            assertTrue("the scene " + name + " has not been set to " + className, mapScenes.containsKey(name) && mapScenes.get(name).equals(className));
        });
        And("the default scene is {string}", (String defaultSceneName) -> {
            assertEquals("the default Scene has not been set correctly to " + defaultSceneName, defaultSceneName, config.defaultScene);
        });

    }
}
