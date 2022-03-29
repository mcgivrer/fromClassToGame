package features;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import fr.snapgames.fromclasstogame.test.TestUtils;
import fr.snapgames.fromclasstogame.test.entity.TestObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.junit.Assert.*;

public class RenderHelperStepDefs extends CommonDefSteps {

    @Given("The Game start with default config")
    public void theGameStartWithDefaultConfig() {
        game = new Game("test-render");
        game.testMode = true;
        try {
            game.run(new String[]{});
        } catch (ArgumentUnknownException e) {
            fail("Unable to start Game");
        }
    }

    @Then("the Render is ready")
    public void theRenderIsReady() {
        assertNotNull("Render has not been initialized", game.getRenderer());
    }

    @And("the RenderHelper for {string} is ready")
    public void theRenderHelperForIsReady(String objectName) {
        Renderer renderer = (Renderer) SystemManager.get(Renderer.class);
        Map<String, RenderHelper<?>> renderHelpers = renderer.getRenderHelpers();
        RenderHelper<?> rh = renderHelpers.get(objectName);
        assertEquals("The '" + objectName + "' RenderHelper is not defined", objectName, rh.getType());
    }

    @And("I add a new RenderHelper {string} for a {string}")
    public void iAddANewForA(String className, String EntityName) {
        try {
            Class<?> rhc = Class.forName(className);

            Renderer renderer = (Renderer) SystemManager.get(Renderer.class);
            RenderHelper<?> rh = (RenderHelper<?>) rhc.getConstructors()[0].newInstance(renderer);
            renderer.addRenderHelper(rh);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            fail("Unable to add the RenderHelper named " + className + ": " + e.getMessage());
        }
    }

    @And("the TestObject named {string} is rendered.")
    public void theTestObjectNamedIsRendered(String objectName) {

        Renderer renderer = (Renderer) SystemManager.get(Renderer.class);
        Scene sc = ((SceneManager) SystemManager.get(SceneManager.class)).getCurrent();
        renderer.draw(1);
        TestObject to = (TestObject) sc.getGameObject("test");
        assertTrue("The TestObject " + objectName + " has not been rendered", to.getFlag());
    }

    @And("I add a {string} as Scene")
    public void iAddATestScene(String sceneName) {
        SceneManager sm = (SceneManager) SystemManager.get(SceneManager.class);
        sm.addScene(sceneName);
        TestUtils.createSceneInstance(sceneName);
    }

    @And("I add a TestObject named {string}")
    public void iAddATestObjectNamed(String objectName) {
        Scene sc = TestUtils.getCurrentScene();
        sc.add(new TestObject(objectName, 0, 0));
    }
}
