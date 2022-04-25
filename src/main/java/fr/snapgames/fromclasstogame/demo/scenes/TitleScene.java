package fr.snapgames.fromclasstogame.demo.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.DebugSwitcherBehavior;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.I18n;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.core.system.SystemManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleScene extends AbstractScene<TitleScene> {

    Font screenFont;
    BufferedImage bckImage;
    BufferedImage logoImage;
    Dimension vp;

    double accelX = 20;
    double accelY = 20;

    public TitleScene(Game g) {
        super(g, "title");
    }

    @Override
    public void initialize(Game g) {
        super.initialize(g);
        try {
            // font for title and text display
            screenFont = ResourceManager.getFont("./fonts/FreePixel.ttf");
            // Background image resource
            bckImage = ResourceManager.getSlicedImage("images/backgrounds/volcano.png", "background", 100, 21, 800, 600);
            // Icon logo SnapGames
            logoImage = ResourceManager.getImage("images/logo/sg-logo-image.png");
        } catch (UnknownResource e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Game g) throws UnknownResource {
        super.create(g);
        if (g.getConfiguration().debugLevel > 0) {
            // Add the Debug switcher capability to this scene
            addBehavior(new DebugSwitcherBehavior());
        }

        // Define the camera following the player object.
        BufferedImage buffer = g.getRenderer().getBuffer();
        vp = new Dimension(buffer.getWidth(), buffer.getHeight());

        // add a background image
        GameObject bckG = new GameObject("background", new Vector2d(0.0, 0.0))
                .setImage(ResourceManager.getImage("images/backgrounds/volcano.png:background"))
                .setObjectType(GameObject.GOType.IMAGE)
                .setLayer(10)
                .setPriority(1)
                .setDebug(3);

        add(bckG);

        // add Logo.
        GameObject logo = new GameObject("logo",
                new Vector2d(10, 7.5 * (vp.getHeight() / 10)))
                .setImage(ResourceManager.getImage("images/logo/sg-logo-image.png"))
                .setObjectType(GameObject.GOType.IMAGE)
                .setLayer(5)
                .setPriority(5)
                .setRelativeToCamera(true);
        add(logo);

        // add Title
        Graphics r = game.getRenderer().getGraphics();
        Font titleFont = screenFont.deriveFont(16.0f);
        Font textFont = screenFont.deriveFont(8.0f);

        r.setFont(titleFont);
        String textTitle = I18n.getMessage("scene.title.main.text");
        TextObject gameTitle = new TextObject("gameTitle",
                new Vector2d(
                        (vp.width - r.getFontMetrics().stringWidth(textTitle)) / 2,
                        (vp.height / 6.0) * 2.0))
                .setText(textTitle)
                .setFont(titleFont);
        gameTitle.setColor(Color.WHITE)
                .setPriority(1)
                .setLayer(1)
                .setRelativeToCamera(true);
        add(gameTitle);

        String textStart = I18n.getMessage("scene.title.start.text");
        r.setFont(textFont);
        TextObject startTextObject = new TextObject("startText",
                new Vector2d(
                        (vp.width - r.getFontMetrics().stringWidth(textStart)) / 2,
                        (vp.height / 6.0) * 4.0))
                .setText(textStart)
                .setFont(titleFont);
        startTextObject.setColor(Color.WHITE)
                .setPriority(1)
                .setLayer(1)
                .setRelativeToCamera(true);
        add(startTextObject);

        // Add camera
        Camera camera = new Camera("cam01")
                .setTarget(gameTitle)
                .setTweenFactor(0.02)
                .setViewport(vp);
        add(camera);
    }

    @Override
    public void dispose() {
        ResourceManager.clear();
    }

    @Override
    public void input(ActionHandler ah) {
        super.input(ah);
        GameObject bckGo = getGameObject("background");
        bckGo.forces.add(new Vector2d(accelX, accelY));
    }

    @Override
    public void update(long dt) {
        super.update(dt);
        GameObject bckGo = getGameObject("background");
        if (bckGo.position.x <= 0 || bckGo.position.x + bckGo.width >= vp.width) {
            accelX *= -1;
        }
        if (bckGo.position.y <= 0 || bckGo.position.y + bckGo.height >= vp.height) {
            accelY *= -1;
        }
    }

    @Override
    public void onAction(Integer a) {
        super.onAction(a);
        switch (a) {
            case ActionHandler.FIRE1:
            case ActionHandler.FIRE2:
            case ActionHandler.FIRE3:
            case ActionHandler.FIRE4:
            case ActionHandler.START:
                ((SceneManager) SystemManager.get(SceneManager.class)).activate("play");
                break;
        }
    }
}
