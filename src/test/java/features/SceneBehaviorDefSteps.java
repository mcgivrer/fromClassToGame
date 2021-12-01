package features;

import fr.snapgames.fromclasstogame.test.scenes.TestScene;
import io.cucumber.java8.En;

import static org.junit.Assert.assertEquals;

public class SceneBehaviorDefSteps implements En {
    TestScene scene;


    public SceneBehaviorDefSteps() {
        Given("A Scene {string} is created", (String sceneName) -> {
            scene = new TestScene(null, sceneName);
        });
        Then("the Scene {string} has no behavior", (String sceneName) -> {
            assertEquals("", scene.getBehaviors().size(), 0);
        });
    }

}
