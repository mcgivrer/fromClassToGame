package features;

import fr.snapgames.fromclasstogame.core.entity.EntityPool;
import fr.snapgames.fromclasstogame.core.entity.EntityPoolManager;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import io.cucumber.java8.En;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EntityPoolStepdefs extends CommonDefSteps implements En {
    EntityPoolManager epm;
    EntityPool ep;

    public EntityPoolStepdefs() {
        And("I create an EntityPool named {string} with size of {int}", (String epName, Integer epSize) -> {
            ep = new EntityPool(epName, epSize);
        });

        And("I add the EntityPool named {string} to the Scene {string}", (String epName, String sceneName) -> {
            epm.addPool(ep);
        });

        Then("I get the EntityPool named {string} with {int} available items", (String epName, Integer epRequestedSize) -> {
            Assert.assertEquals("The EntityPool so=ize does not match " + epRequestedSize + " items", epRequestedSize.intValue(), ep.getSize());
        });

        Given("An EntityPoolManager is created", () -> {
            epm = new EntityPoolManager(null);
        });

        And("I add the EntityPool named {string}", (String epName) -> {
            epm.addPool(ep);
        });

        Given("An EntityPoolManager is created with EntityPools {string}", (String entityPoolNames) -> {
            epm = new EntityPoolManager(null);
            if (entityPoolNames.contains(",")) {
                for (String epName : entityPoolNames.split(",")) {
                    epm.addPool(new EntityPool(epName, 10));
                }
            } else {
                epm.addPool(new EntityPool(entityPoolNames, 10));
            }
        });

        And("I remove the EntityPool {string}", (String epName) -> {
            epm.removePool(epName);
        });

        Then("the EntityPoolManager has no more {string}", (String epName) -> {
            assertNull("the EntityPoolManager has " + epName, epm.get(epName));
        });

        And("I add a GameObject {string} to the EntityPool {string}", (String goName, String epName) -> {
            GameObject go = new GameObject(goName);
            epm.get(epName).add(go);
        });

        Then("the EntityPool {string} contains the GameObject {string}", (String epName, String goName) -> {
            GameObject go = epm.get(epName).get(goName);
            assertEquals("", goName, go.name);
        });

    }

}
