package features;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import fr.snapgames.fromclasstogame.test.entity.TestObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.junit.Assert.*;

public class RenderHelperStepDefs {

    private Game game;

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
        assertNotNull("Render has not been initialized", game.getRender());
    }

    @And("the RenderHelper for {string} is ready")
    public void theRenderHelperForIsReady(String objectName) {
        Render render = (Render) SystemManager.get(Render.class);
        Map<String, RenderHelper> renderHelpers = render.getRenderHelpers();
        RenderHelper rh = renderHelpers.get(objectName);
        assertEquals("The '" + objectName + "' RenderHelper is not defined", objectName, rh.getType());
    }

    @And("I add a new RenderHelper {string} for a {string}")
    public void iAddANewForA(String className, String EntityName) {
        try {
            Class<?> rhc = Class.forName(className);
            RenderHelper rh = (RenderHelper) rhc.getConstructors()[0].newInstance();
            Render render = (Render) SystemManager.get(Render.class);
            render.addRenderHelper(rh);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            fail("Unable to add the RenderHelper named " + className + ": " + e.getMessage());
        }
    }

    @And("the TestObject named {string} is rendered.")
    public void theTestObjectNamedIsRendered(String objectName) {

        Render render = (Render) SystemManager.get(Render.class);
        Scene sc = ((SceneManager) SystemManager.get(SceneManager.class)).getCurrent();
        render.render();
        TestObject to = (TestObject) sc.getGameObject("test");
        assertTrue("The TestObject " + objectName + " has not been rendered", to.getFlag());
    }

    @And("I add a {string} as scene")
    public void iAddATestScene(String sceneName) {
        SceneManager sm = (SceneManager) SystemManager.get(SceneManager.class);
        sm.addScene(sceneName);
    }

    @And("I add a TestObject named {string}")
    public void iAddATestObjectNamed(String arg0) {
        Scene sc = ((SceneManager) SystemManager.get(SceneManager.class)).getCurrent();
        sc.add(new TestObject("test",0,0));
    }
}
