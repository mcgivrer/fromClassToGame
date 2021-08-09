package features;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.exceptions.cli.UnknownArgumentException;
import fr.snapgames.fromclasstogame.core.gfx.RenderHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;

public class RenderHelperStepDefs {

    private Game game;

    @Given("The Game start with default config")
    public void theGameStartWithDefaultConfig() {
        game = new Game("test-render");
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
    public void theRenderHelperForIsReady(String arg0) {
        Map<String,RenderHelper> renderHelpers = game.getRender().getRenderHelpers();
        assertEquals("The GameObject RenderHelper is not defined","GameObject",renderHelpers.get("GameObject").getType());
        assertEquals("The TextObject RenderHelper is not defined","TextObject",renderHelpers.get("TextObject").getType());
    }
}
