package features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import fr.snapgames.fromclasstogame.core.entity.EntityPool;
import fr.snapgames.fromclasstogame.core.entity.EntityPoolManager;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import io.cucumber.java8.En;

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
            assertEquals(String.format("The EntityPool size does not match %s items", epRequestedSize), epRequestedSize.intValue(), ep.getSize());
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

        Then("The Game has an EntityPoolManager System", () -> {
            assertNotNull("The Game has no EntityPoolManager System", game.getEPM());
        });
        Then("The Game has an EntityPool for GameObject", () -> {
            EntityPool ep = game.getEPM().getPool(GameObject.class.getName());
            assertNotNull("The Game has no default EP for GameObject", ep);
        });
    }
}
