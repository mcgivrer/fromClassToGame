package features;

import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import fr.snapgames.fromclasstogame.test.entity.TestObject;
import io.cucumber.java.en.And;
import io.cucumber.java8.En;
import org.junit.Assert;

import static org.junit.Assert.*;

public class RenderStepdefs extends CommonDefSteps implements En {

    public RenderStepdefs() {

        And("the Render is a System.", () -> {
            Render r = (Render) SystemManager.get(Render.class);
            Assert.assertNotNull("Render is not a managed Game System", r);
        });
        Then("the GameObject named {string} is rendered.", (String renderedObjectName) -> {
            Render r = (Render) SystemManager.get(Render.class);
            boolean go = r.getObjects().stream().anyMatch(o -> o.name.equals(renderedObjectName));
            assertTrue(String.format("no GameObject %s has been rendered", renderedObjectName), go);
        });
        And("I add a Camera named {string} to the current Scene", (String cameraName) -> {
            Scene cs = ((SceneManager) SystemManager.get(SceneManager.class)).getCurrent();
            cs.add(new Camera(cameraName));
        });
        Then("the Render has its current camera set with {string} Camera.", (String cameraName) -> {
            Render r = (Render) SystemManager.get(Render.class);
            assertEquals("The Camera has not been set in the Render system", cameraName, r.getCamera().name);

        });
        And("I add a TextObject named {string}", (String textObjectName) -> {
            Scene cs = ((SceneManager) SystemManager.get(SceneManager.class)).getCurrent();
            cs.add(new TextObject(textObjectName, new Vector2d(0.0, 0.0)));
        });
    }
}
