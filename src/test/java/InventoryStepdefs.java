import fr.snapgames.fromclasstogame.demo.entity.InventoryObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class InventoryStepdefs {

    private InventoryObject inventory;

    @Given("an InventoryObject {string} is created")
    public void anInventoryObjectIsCreated(String name) {
        inventory = new InventoryObject(name, 0, 0);
    }

    @Then("the number of item placeholder is set to {int}.")
    public void theDefaultNumberOfPlaceholderIsSetTo(int nbPlaces) {
        Assert.assertEquals("the number of emtpy places does not match " + nbPlaces, nbPlaces, inventory.getNbPlaces());
    }
}
