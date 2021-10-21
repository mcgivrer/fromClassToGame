package features;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.demo.entity.InventoryObject;
import fr.snapgames.fromclasstogame.test.scenes.TestScene;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.awt.image.BufferedImage;

public class InventoryStepDefs {

    private TestScene testScene;
    private InventoryObject inventory;

    @Given("an InventoryObject {string} is created")
    public void anInventoryObjectIsCreated(String name) {
        testScene = new TestScene(null);
        inventory = new InventoryObject(name, 0, 0);
        testScene.add(inventory);
    }

    @Then("the number of item placeholder is set to {int}.")
    public void theDefaultNumberOfPlaceholderIsSetTo(int nbPlaces) {
        Assert.assertEquals("the number of emtpy places does not match " + nbPlaces, nbPlaces, inventory.getNbPlaces());
    }

    @And("I create a new GameObject {string} with an attribute {string}")
    public void iCreateANewGameObject(String objectName, String attributeName) {
        GameObject item = new GameObject(objectName);
        item.addAttribute("inventory", new BufferedImage(12, 12, BufferedImage.TYPE_4BYTE_ABGR));
        testScene.add(item);
    }

    @And("I add the GameObject {string} to InventoryObject {string}")
    public void iAddTheGameObjectToInventoryObject(String gameObjectName, String inventoryObjectName) {
        InventoryObject invt = (InventoryObject) testScene.getGameObject(inventoryObjectName);
        GameObject itm = testScene.getGameObject(gameObjectName);
        invt.add(itm);
    }

    @Then("the InventoryObject {string} has {int} Item.")
    public void theInventoryObjectHasOneItem(String inventoryObjectName, int nbItems) {
        InventoryObject invt = (InventoryObject) testScene.getGameObject(inventoryObjectName);
        Assert.assertEquals(String.format("The InventoryObject %s does not have the right number of items", inventoryObjectName), nbItems, invt.getItems().size());
    }

}
