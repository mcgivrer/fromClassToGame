package features;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.snapgames.fromclasstogame.core.exceptions.cli.UnknownArgumentException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class GameDefSteps {

    private static final Logger logger = LoggerFactory.getLogger(GameDefSteps.class);
    private Game game;
    private String[] args;
    private List<String> argList = new ArrayList<>();

    @Given("the Game is instantiated")
    public void givenTheGameIsInstantiated() {
        game = new Game("test-scene");
        game.testMode = true;
    }

    @When("I prepare the arguments")
    public void andIPrepareTheArgument() {
        argList.clear();
        args = new String[]{};
    }

    @And("I add argument {string}")
    public void andIAddArgumentString(String arg) {

        argList.add(arg);
        args = new String[argList.size() + 1];
        int i = 0;
        for (String a : argList) {
            args[i++] = a;
        }
        args[i++] = arg;
    }

    @And("a window of {int} x {int} is created")
    public void andAWindowOfIntXIntIsCreated(int w, int h) {
        assertEquals("The Window width is not set to " + w, w, game.getWindow().getFrame().getWidth());
        assertEquals("The Window height is not set to " + h, h, game.getWindow().getFrame().getHeight());
    }

    @And("the window title is {string}")
    public void andTheTitleIsString(String title) {
        assertEquals("The window title is not set to" + title, title, game.getWindow().getFrame().getTitle());
    }

    @Then("the Game is running")
    public void thenTheGameIsRunning() {
        try {
            if (args != null) {
                game.run(args);
                game.getSceneManager().activate();
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
            game.run(args);
            fail("Bad argument does not raise exception");
        } catch (UnknownArgumentException e) {
            logger.error("Unable to run the game", e);
        }
    }

    @Given("I add a GameObject named {string} at \\({double},{double})")
    public void givenIAddAGameObjectNamedStringAtIntInt(String name, Double x, Double y) {
        GameObject go = new GameObject(name, x, y);
        Scene scene = game.getSceneManager().getCurrent();
        scene.add(go);
    }

    @Given("the {string} size is {int} x {int}")
    public void givenTheStringSizeIsIntXInt(String name, int w, int h) {
        Scene scene = game.getSceneManager().getCurrent();
        GameObject go = scene.getGameObject(name);
        go.setSize(w, h);
    }

    @Then("the Game has {int} GameObject at window center.")
    public void thenTheGameHasIntGameObjectAtWindowCenter(int i) {
        GameObject go = game.getSceneManager().getCurrent().getObjectsList().get(0);
        assertEquals("The Game object list has not the right number of object", i,
                game.getSceneManager().getCurrent().getObjectsList().size());
        assertEquals("the GameObject is not horizontally centered", game.getRender().getBuffer().getWidth() / 2, go.x, 0.0);
        assertEquals("the GameObject is not vertically centered", game.getRender().getBuffer().getHeight() / 2, go.y, 0.0);
    }

    @Then("the Game has {int} GameObject\\(s).")
    public void theGameHasGameObjectS(int nbObjects) {
        Scene scene = game.getSceneManager().getCurrent();
        assertEquals("The Scene has not the right number of objects", nbObjects, scene.getObjectsList().size());
    }

    @And("the {string} GameObject speed is set to \\({double},{double})")
    public void theGameObjectSpeedIsSetTo(String name, Double dx, Double dy) {
        Scene scene = game.getSceneManager().getCurrent();
        GameObject go = scene.getGameObject(name);
        go.setSpeed(dx, dy);
    }

    @Then("I update {int} times the scene")
    public void iUpdateTimesTheScene(int nbUpdate) {
        Scene scene = game.getSceneManager().getCurrent();
        for (int i = 0; i < nbUpdate; i++) {
            scene.update((long) (1000.0 / game.getConfiguration().FPS));
        }
    }

    @And("the {string} GameObject is now at \\({double},{double})")
    public void theGameObjectIsNowAt(String name, Double x, Double y) {
        Scene scene = game.getSceneManager().getCurrent();
        GameObject go = scene.getGameObject(name);
        assertEquals("the GameObject " + name + " is not horizontally centered", x, go.x, 0.0);
        assertEquals("the GameObject " + name + " is not vertically centered", y, go.y, 0.0);
    }

    @And("The font {string} is added")
    public void theFontIsAdded(String fontPath) {
        ResourceManager.getFont(fontPath);
    }

    @And("The image {string} is added")
    public void theImageIsAdded(String imagePath) {
        BufferedImage img = null;
        try {
            img = ResourceManager.getImage(imagePath);
        } catch (UnknownResource e) {
            assertNull("Error while getting resource: " + e.getMessage(), img);
        }
    }

    @Then("the ResourceManager has {int} resources")
    public void theResourceManagerHasResources(int nbResources) {
        assertEquals("The number of resources does not match with loaded ones.", nbResources, ResourceManager.getResources().size());
    }

    @And("The image {string} as {string} is sliced at \\({int},{int}) sizing \\({int},{int})")
    public void theImageAsIsSlicedAtSizing(String resourcePath, String name, Integer x, Integer y, Integer w, Integer h) {
        ResourceManager.getSlicedImage(resourcePath, name, x, y, w, h);

    }

    @And("The resulting {string} image sizing \\({int},{int})")
    public void theResultingImageSizing(String pathAndName, int w, int h) {
        BufferedImage img = null;
        try {
            img = ResourceManager.getImage(pathAndName);
        } catch (UnknownResource e) {
            fail("Error n loading resource: " + e.getMessage());
        }
        assertEquals("the image resource " + pathAndName + " has not been horizontally sliced correctly", w, img.getWidth());
        assertEquals("the image resource " + pathAndName + " has not been vertically sliced correctly", h, img.getHeight());
    }

    @And("the resources are cleared")
    public void theResourcesAreCleared() {
        ResourceManager.clear();
    }

    @And("I add a TextObject named {string} at \\({double},{double})")
    public void iAddATextObjectNamedAt(String name, Double x, Double y) {
        Scene scene = game.getSceneManager().getCurrent();
        TextObject to = new TextObject(name, x, y);
        scene.add(to);
    }

    @And("the text for {string} is {string}")
    public void theTextForIs(String name, String text) {
        Scene scene = game.getSceneManager().getCurrent();
        TextObject to = (TextObject) scene.getGameObject(name);
        to.setText(text);
    }

    @And("the TextObject default color for {string} is White")
    public void theTextObjectDefaultColorForIsWhite(String name) {
        Scene scene = game.getSceneManager().getCurrent();
        TextObject to = (TextObject) scene.getGameObject(name);
        assertEquals("Default color for Text Object is not White", Color.WHITE, to.color);
    }

    @Then("the TextObject default font for {string} is null")
    public void theTextObjectDefaultFontForIsNull(String name) {
        Scene scene = game.getSceneManager().getCurrent();
        TextObject to = (TextObject) scene.getGameObject(name);
        assertNull("The default TextObject font is not null", to.font);
    }
}
