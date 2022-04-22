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
            Scene current = getGame().getSceneManager().getCurrent();
            current.add(new LightObject(ambientLightName, new Vector2d(0.0, 0.0), LightType.LIGHT_AMBIANT));
        });

        And("I add an spherical LightObject named {string}", (String sphericalLightName) -> {
            Scene current = getGame().getSceneManager().getCurrent();
            current.add(new LightObject(sphericalLightName, new Vector2d(0.0, 0.0), LightType.LIGHT_SPHERE));
        });
        Then("The LightObject {string} is rendered", (String lightName) -> {
            Scene current = getGame().getSceneManager().getCurrent();
            LightObject lo = (LightObject) current.getGameObject(lightName);
            assertTrue("The LightObject " + lightName + " has not been rendered", lo.rendered);
        });
        And("I add the LightObjectRenderHelper", () -> {
            getGame().getRenderer().addRenderHelper(new LightObjectRenderHelper(getGame().getRenderer()));
        });
        Then("the game renders the scene", () -> {
            Scene current = getGame().getSceneManager().getCurrent();
            getGame().getRenderer().draw();
        });

        And("I set the light {string} intensity to {double}", (String lightName, Double intensity) -> {
            Scene current = getGame().getSceneManager().getCurrent();
            LightObject lo = (LightObject) current.getGameObject(lightName);
            lo.setIntensity(intensity);
        });
        And("I set the light {string} color to {string}", (String lightName, String colorName) -> {
            Scene current = getGame().getSceneManager().getCurrent();
            LightObject lo = (LightObject) current.getGameObject(lightName);
            lo.setColor(convertColorFromString(colorName));
        });
        And("I set the light {string} glitterEffect to {double}", (String lightName, Double glitterEffect) -> {
            Scene current = getGame().getSceneManager().getCurrent();
            LightObject lo = (LightObject) current.getGameObject(lightName);
            lo.setGlitterEffect(glitterEffect);
        });
        And("I set the light {string} radius to {double}", (String lightName, Double radius) -> {
            Scene current = getGame().getSceneManager().getCurrent();
            LightObject lo = (LightObject) current.getGameObject(lightName);
            lo.setRadius(radius);
        });
    }

    private Color convertColorFromString(String colorName) {
        Color color = null;
        switch (colorName.toUpperCase()) {
            case "WHITE":
                color = Color.WHITE;
                break;
            case "RED":
                color = Color.RED;
                break;
            case "GREEN":
                color = Color.GREEN;
                break;
            case "BLUE":
                color = Color.BLUE;
                break;
            case "BLACK":
                color = Color.BLACK;
                break;
            case "YELLOW":
                color = Color.YELLOW;
                break;
            default:
                color = Color.LIGHT_GRAY;
                break;
        }
        return color;
    }
}
