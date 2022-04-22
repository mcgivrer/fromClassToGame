package features;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.system.System;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import io.cucumber.java8.En;

public class SystemManagerStepDefs extends CommonDefSteps implements En {
    SystemManager sm;

    public SystemManagerStepDefs() {
        When("I create a SystemManager", () -> {
            setGame(new Game("test-sm"));
            sm = SystemManager.configure(getGame());
        });
        Then("the SystemManager instance is not null", () -> {
            assertNotNull("The System manager instance has not been initialized", SystemManager.getInstance());
        });
        And("I add a new {string}", (String className) -> {
            Class<? extends System> classClass = (Class<? extends System>) Class.forName(className);
            SystemManager.add(classClass);
        });
        Then("I can retrieve {string} from the SystemManager", (String className) -> {
            Class<? extends System> classClass = (Class<? extends System>) Class.forName(className);
            System s = SystemManager.get(classClass);
            assertNotNull("The system does not exists", s);
        });
        When("I create a SystemManager with a {string}", (String className) -> {
            sm = SystemManager.configure(getGame());
            Class<? extends System> classClass = (Class<? extends System>) Class.forName(className);
            SystemManager.add(classClass);
        });
        And("I remove {string}", (String className) -> {
            Class<? extends System> classClass = (Class<? extends System>) Class.forName(className);
            System s = SystemManager.get(classClass);
            assertNotNull("the requested service does not exists", s);
            SystemManager.remove(classClass);
        });
        Then("I can't get the {string} as System", (String className) -> {
            Class<? extends System> classClass = (Class<? extends System>) Class.forName(className);
            System s = SystemManager.get(classClass);
            assertNull("The system has not been removed", s);
        });
        And("I terminate the SystemManager", SystemManager::dispose);
        Then("all the systems are disposed and free", () -> {
            assertTrue("", SystemManager.getSystems().isEmpty());
        });
        And("I initialize SystemManager", () -> {
            SystemManager.configure(getGame());
        });
        Then("all the Systems are ready", () -> {
            for (System system : SystemManager.getSystems()) {
                assertTrue("The system " + system.getName() + "was not ready", system.isReady());
            }
        });
    }
}
