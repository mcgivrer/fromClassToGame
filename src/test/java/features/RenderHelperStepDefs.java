package features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.exceptions.cli.UnknownArgumentException;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.gfx.RenderHelper;
import fr.snapgames.fromclasstogame.test.entity.TestObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class RenderHelperStepDefs {

    private Game game;

    @Given("The Game start with default config")
    public void theGameStartWithDefaultConfig() {
        game = new Game("test-render");
        game.testMode = true;
        try {
            game.run(new String[]{});
        } catch (UnknownArgumentException e) {
            fail("Unable to start Game");
        }
    }

    @Then("the Render is ready")
    public void theRenderIsReady() {
        assertNotNull("Render has not been initialized", game.getRender());
    }

    @And("the RenderHelper for {string} is ready")
    public void theRenderHelperForIsReady(String objectName) {
        Map<String, RenderHelper> renderHelpers = game.getRender().getRenderHelpers();
        RenderHelper rh = renderHelpers.get(objectName);
        assertEquals("The '"+objectName+"' RenderHelper is not defined", objectName, rh.getType());
    }

    @And("I add a new {string} for a {string}")
    public void iAddANewForA(String className, String EntityName) {
        try {
            Class<?> rhc = Class.forName(className);
            RenderHelper rh = (RenderHelper) rhc.getConstructors()[0].newInstance();
            Render render =  game.getRender();
            render.addRenderHelper(rh);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            fail("Unable to add the RenderHelper named " + className + ": " + e.getMessage());
        }
    }

    @And("the TestObject named {string} is rendered.")
    public void theTestObjectNamedIsRendered(String objectName) {
        Render render =  game.getRender();
        render.render();
        TestObject to = (TestObject) game.getSceneManager().getCurrent().getGameObject("test");
        assertTrue("The TestObject " + objectName + " has not been rendered", to.getFlag());
    }

    @And("I add a {string} as scene")
    public void iAddATestScene(String sceneName) {
        game.getSceneManager().addScene(sceneName);
    }
}
