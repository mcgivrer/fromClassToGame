package features;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.test.scenes.TestScene;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.awt.*;

import static fr.snapgames.fromclasstogame.test.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SceneDefSteps extends CommonDefSteps {

    @Given("the Game is started with config {string}")
    public void theGameIsStartedWithConfig(String configFile) {
        setGame(new Game(configFile));
        try {
            getGame().initialize(null);
        } catch (ArgumentUnknownException e) {
            System.err.println("Error parsing args " + e.getMessage());
        }
        getGame().testMode = true;
    }

    @Then("the title is {string}")
    public void theTitleIs(String title) {
        try {
            getGame().run(new String[]{"title=" + title});
        } catch (ArgumentUnknownException uae) {
            fail("Uknown Argument");
        }

    }

    @Given("I add an Entity named {string} at {double},{double}")
    public void iAddAnEntityNamedAt(String name, Double x, Double y) {
        GameObject go = new GameObject(name, new Vector2d(x, y));
        Scene sc = getCurrentScene();
        sc.add(go);
    }


    @Given("I add a {string} named {string}")
    public void iAddANamed(String sceneClassName, String sceneName) {
        SceneManager sc = getGame().getSceneManager();
        sc.addScene(sceneName + ":" + sceneClassName);
    }

    @Then("the SceneManager has {int} scene\\(s)")
    public void theSceneManagerHasScenes(Integer nbScene) {
        SceneManager sc = getGame().getSceneManager();
        assertEquals("The number of scene does not match the configuration set", nbScene.intValue(),
                sc.getScenes().size());
    }

    @Then("I activate the scene {string}")
    public void iCanActivateTheScene(String sceneName) {
        activateScene(sceneName);
    }

    @Then("I can dispose all scenes.")
    public void iCanDisposeAllScenes() {
        SceneManager sc = getGame().getSceneManager();
        sc.dispose();
        assertEquals("All scene definition have not been cleared", 0, sc.getScenes().size());
        assertEquals("All scene instance have not been cleared", 0, sc.getScenesInstances().size());
    }

    @And("the camera {string} is declared.")
    public void theCameraIsDeclared(String cameraName) {
        Camera cam = getCurrentScene().getCamera(cameraName);
        assertEquals("The camera " + cameraName + " was not declared", cameraName, cam.name);
    }

    @And("the camera {string} is active.")
    public void theCameraIsActive(String cameraName) {
        Camera cam = getCurrentScene().getActiveCamera();
        assertEquals("The camera " + cameraName + " was not the default active one", cameraName, cam.name);
    }

    @And("the Camera {string} position is centered on the {string} position.")
    public void theCameraPositionIsCenteredOnTargetPosition(String cameraName, String targetName) {
        Dimension viewport = getGame().getRenderer().getViewport();
        Camera cam = getCurrentScene().getCamera(cameraName);
        GameObject target = getCurrentScene().getGameObject(targetName);
        double camX = cam.position.x + (viewport.width * 0.5) - (target.width);
        double camY = cam.position.y + (viewport.height * 0.5) - (target.height);
        assertEquals(
                "The horizontal camera " + cameraName + " position does not match target " + targetName + " position",
                target.position.x, camX, 1);
        assertEquals(
                "The vertical camera " + cameraName + " position does not match target " + targetName + " position",
                target.position.y, camY, 1);
    }

    @And("I add a Scene named {string};")
    public void iAddASceneNamed(String sceneName) {
        TestScene ts = new TestScene(getGame(), sceneName);
        addScene(sceneName, ts);
    }


    @And("I add a GameObject named {string} at \\({double},{double}) sizing \\({double},{double}) to Scene {string}")
    public void iAddAGameObjectNamedAtSizingToScene(String objectName, double x, double y, double w, double h,
                                                    String sceneName) {
        GameObject go = new GameObject(objectName, new Vector2d(x, y)).setSize(w, h);
        Scene s = getScene(sceneName);
        s.add(go);
    }

    @And("I add a Camera {string} targeting {string} with factor of {double} to Scene {string}")
    public void iAddACameraTargetingWithFactorOfToScene(String cameraName, String targetName, double tweenFactor,
                                                        String sceneName) {
        Scene s = getScene(sceneName);
        GameObject target = s.getGameObject(targetName);
        Camera cam = new Camera(cameraName).setTarget(target).setTweenFactor(tweenFactor);
        cam.setViewport(getGame().getRenderer().getViewport());
        s.add(cam);
    }


    @And("the Camera {string} has a viewport of \\({int},{int}) from scene {string}")
    public void theCameraHasAViewportOf(String cameraName, int width, int height, String sceneName) {
        Scene scene = getScene(sceneName);
        Camera cam = scene.getCamera(cameraName);
        cam.setViewport(new Dimension(width, height));
        scene.add(cam);
    }

    @Then("I update scene {string} for {int} times")
    public void iUpdateTimesTheScene(String sceneName, int nbUpdate) {
        Scene scene = getScene(sceneName);
        for (int i = 0; i < nbUpdate; i++) {
            scene.update((long) (1000.0 / getGame().getConfiguration().FPS));
        }
    }

    @And("the Scene {string} has {int} GameObject.")
    public void theSceneHasGameObject(String sceneName, int nbObjects) {
        Scene scene = getScene(sceneName);
        assertEquals("The scene has not " + nbObjects + " objects", nbObjects, scene.getObjectsList().size());
    }


    @And("the current scene is {string}")
    public void theCurrentSceneIs(String sceneName) {
        Scene scn = getScene(sceneName);
        assertEquals("The current scene is not " + sceneName, sceneName, scn.getName());
    }

    @And("I add ParticleSystem object named {string} to scene {string}")
    public void iAddParticleSystemObjectNamedToScene(String psName, String sceneName) {
        Scene scn = getScene(sceneName);
        scn.add(new ParticleSystem(psName, new Vector2d(0, 0)));
    }
}
