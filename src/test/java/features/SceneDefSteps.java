package features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.core.exceptions.cli.UnknownArgumentException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SceneDefSteps {

    Game game;

    @Given("the Game is started with config {string}")
    public void theGameIsStartedWithConfig(String configFile) {
        game = new Game(configFile);
        try {
            game.initialize(null);
        } catch (UnknownArgumentException e) {
            e.printStackTrace();
        }
        game.testMode = true;
    }

    @Then("the title is {string}")
    public void the_title_is(String title) {
        try {
            game.run(new String[]{"title=" + title});
        } catch (UnknownArgumentException uae) {
            fail("Uknown Argument");
        }

    }

    @Given("I add an Entity named {string} at {double},{double}")
    public void i_add_an_entity_named_at(String name, Double x, Double y) {
        GameObject go = new GameObject(name, x, y);
        Scene sc = game.getSceneManager().getCurrent();
        sc.add(go);
    }

    @Given("I add a {string} named {string}")
    public void i_add_a_named(String sceneClassName, String sceneName) {
        SceneManager sc = game.getSceneManager();
        sc.addScene(sceneName + ":" + sceneClassName);
    }

    @Then("the SceneManager has {int} scene\\(s)")
    public void the_scene_manager_has_scene_s(Integer nbScene) {
        SceneManager sc = game.getSceneManager();
        assertEquals("The number of scene does not match the configuration set", nbScene.intValue(),
                sc.getScenes().size());
    }

    @Then("I can activate the scene {string}")
    public void i_can_activate_the_scene(String sceneName) {
        SceneManager sc = game.getSceneManager();
        sc.activate(sceneName);
    }

    @Then("I can dispose all scenes.")
    public void i_can_dispose_all_scenes() {
        SceneManager sc = game.getSceneManager();
        sc.dispose();
        assertEquals("All scene definition have not been cleared",0,sc.getScenes().size());
        assertEquals("All scene instance have not been cleared",0,sc.getScenesInstances().size());
    }

}
