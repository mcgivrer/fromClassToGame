package features;

import features.CommonDefSteps;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.test.behaviors.TestBehavior;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class GameObjectBehabiorStepdefs extends CommonDefSteps {
    private GameObject go;

    @Given("a new GameObject named {string} at \\({double},{double})")
    public void aNewGameObjectNamedAt(String name, Double x, Double y) {
        go = new GameObject(name, new Vector2d(x,y));
    }

    @Then("the GameObject {string} has no behavior.")
    public void theGameObjectHasNoBehavior(String arg0) {
        Assert.assertEquals("The just created GameObject have behavior !", 0, go.behaviors.size(), 0);
    }

    @And("I add a TestBehavior to the GameObject {string}")
    public void iAddATestBehaviorToTheGameObject(String name) {
        TestBehavior tb = new TestBehavior();
        go.add(tb);
    }

    @Then("the GameObject {string} has {int} behavior\\(s)")
    public void theGameObjectHasBehaviorS(String name, int nbBehaviors) {
        Assert.assertEquals(String.format("The GameObject has not %d behaviors", nbBehaviors), nbBehaviors, go.behaviors.size());
    }
}
