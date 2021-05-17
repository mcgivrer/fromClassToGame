package features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import fr.snapgames.fromclasstogame.Game;

public class GameDefSteps {

    private static final Logger logger = LoggerFactory.getLogger(GameDefSteps.class);
    private Game game;
    private String[] args = new String[] {};
    private List<String> argList = new ArrayList<>();

    @Given("the Game is instantiated")
    public void givenTheGameIsInstantiated() {
        game = new Game();
        game.testMode = true;
    }

    @And("I prepare the arguments")
    public void andIPrepareTheArgument() {
        argList.clear();
        args = new String[] {};
    }

    @And("I add argument \"([^\"]*)\"")
    public void andIAddArgumentString(String arg) {

        argList.add(arg);
        args = new String[argList.size() + 1];
        int i = 0;
        for (String a : argList) {
            args[i++] = a;
        }
        args[i++] = arg;
    }

    @And("I add argument/value \"([^\"]*)\"=\"([^\"]*)\"")
    public void andIAddArgValueStringEqString(String arg, String value) {
        andIAddArgumentString(arg + "=" + value);
    }

    @And("a window of (\\d+) x (\\d+) is created")
    public void andAWindowOfIntXIntIsCreated(int w, int h) {
        assertEquals("The Window width is not set to " + w, w, game.getFrame().getWidth());
        assertEquals("The Window height is not set to " + h, h, game.getFrame().getHeight());
    }

    @And("the title is \"([^\"]*)\"")
    public void andTheTitleIsString(String title) {
        assertEquals("The window title is not set to" + title, title, game.getFrame().getTitle());
    }

    @Then("the Game is running")
    public void thenTheGameIsRunning() {
        try {

            if (args != null) {
                game.run(args);
            } else {
                game.run(null);
            }
        } catch (Exception e) {
            logger.error("Unable to run the game", e);
        }
    }

    @Then("the Game raises exception")
    public void thenTheGameRaisesException() {
        try {
            if (args != null) {
                game.run(args);
            } else {
                game.run(null);
            }
            fail("Bad argument does not raise exception");
        } catch (Exception e) {
            logger.error("Unable to run the game", e);
        }

    }

}
