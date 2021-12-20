package features;

import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import fr.snapgames.fromclasstogame.test.behaviors.TestSceneBehavior;
import fr.snapgames.fromclasstogame.test.scenes.TestScene;
import io.cucumber.java8.En;

import static org.junit.Assert.assertEquals;

public class SceneBehaviorDefSteps extends CommonDefSteps implements En {
    TestScene scene;

    public SceneBehaviorDefSteps() {
        Given("A Scene {string} is created", (String sceneName) -> {
            scene = new TestScene(null, sceneName);
        });

        Then("the Scene {string} has no behavior", (String sceneName) -> {
            assertEquals("", scene.getBehaviors().size(), 0);
        });

        And("I add a Behavior TestBehavior to the scene {string}", (String sceneName) -> {
            scene.addBehavior(new TestSceneBehavior());
        });

        Then("the Scene {string} has {int} behavior", (String sceneName, Integer nbBehaviors) -> {
            assertEquals("The number of behaviors in the scene does nt match expected one",
                    nbBehaviors,
                    java.util.Optional.of(scene.getBehaviors().size()).get());
        });
    }

}

