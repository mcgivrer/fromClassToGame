package features;


import fr.snapgames.fromclasstogame.core.entity.LightObject;
import fr.snapgames.fromclasstogame.core.entity.LightType;
import fr.snapgames.fromclasstogame.core.gfx.renderer.LightObjectRenderHelper;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.scenes.Scene;
import io.cucumber.java8.En;

import java.awt.*;

import static org.junit.Assert.assertTrue;

public class LightStepdefs extends CommonDefSteps implements En {
    public LightStepdefs() {
        And("I add an ambient LightObject named {string}", (String ambientLightName) -> {
            Scene current = game.getSceneManager().getCurrent();
            current.add(new LightObject(ambientLightName, new Vector2d(0.0, 0.0), LightType.LIGHT_AMBIANT));
        });

        And("I add an spherical LightObject named {string}", (String sphericalLightName) -> {
            Scene current = game.getSceneManager().getCurrent();
            current.add(new LightObject(sphericalLightName, new Vector2d(0.0, 0.0), LightType.LIGHT_SPHERE));
        });
        Then("The LightObject {string} is rendered", (String lightName) -> {
            Scene current = game.getSceneManager().getCurrent();
            LightObject lo = (LightObject) current.getGameObject(lightName);
            assertTrue("The LightObject " + lightName + " has not been rendered", lo.rendered);
        });
        And("I add the LightObjectRenderHelper", () -> {
            Scene current = game.getSceneManager().getCurrent();
            game.getRender().addRenderHelper(new LightObjectRenderHelper(game.getRender()));
        });
        Then("the game renders the scene", () -> {
            Scene current = game.getSceneManager().getCurrent();
            game.getRender().render();
        });
        And("I set the light {string} intensity to {double}", (String lightName, Double intensity) -> {
            Scene current = game.getSceneManager().getCurrent();
            LightObject lo = (LightObject) current.getGameObject(lightName);
            lo.setIntensity(intensity);
        });
        And("I set the light {string} color to {string}", (String lightName, String colorName) -> {
            Scene current = game.getSceneManager().getCurrent();
            LightObject lo = (LightObject) current.getGameObject(lightName);
            switch (colorName.toUpperCase()) {
                case "WHITE":
                    lo.setForegroundColor(Color.WHITE);
                    break;
                case "RED":
                    lo.setForegroundColor(Color.RED);
                    break;
                case "GREEN":
                    lo.setForegroundColor(Color.GREEN);
                    break;
                case "BLUE":
                    lo.setForegroundColor(Color.BLUE);
                    break;
                case "BLACK":
                    lo.setForegroundColor(Color.BLACK);
                    break;
                case "YELLOW":
                    lo.setForegroundColor(Color.YELLOW);
                    break;
                default:
                    break;
            }
        });
        And("I set the light {string} glitterEffect to {double}", (String lightName, Double glitterEffect) -> {
            Scene current = game.getSceneManager().getCurrent();
            LightObject lo = (LightObject) current.getGameObject(lightName);
            lo.setGlitterEffect(glitterEffect);
        });
    }
}
