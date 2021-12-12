package fr.snapgames.fromclasstogame.demo.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.DebugSwitcherBehavior;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.core.system.SystemManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleScene extends AbstractScene {

    Font titleFont;
    BufferedImage bckImage;
    BufferedImage logoImage;
    Dimension vp;

    public TitleScene(Game g) {
        super(g, "title");
    }

    @Override
    public void initialize(Game g) {
        super.initialize(g);
        try {
            // font for title and text display
            titleFont = ResourceManager.getFont("./fonts/FreePixel.ttf");
            // Background image resource
            bckImage = ResourceManager.getSlicedImage("images/backgrounds/volcano.png", "background", 0, 0, 1008, 642);
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
        vp = new Dimension(g.getRender().getBuffer().getWidth(), g.getRender().getBuffer().getHeight());

        // add a background image
        GameObject bckG = new GameObject("background", Vector2d.ZERO)
                .setImage(ResourceManager.getImage("images/backgrounds/volcano.png:background"))
                .setType(GameObject.GOType.IMAGE)
                .setLayer(100)
                .setPriority(100)
                .setAcceleration(new Vector2d(0.02, -0.03));
        add(bckG);

        // add Logo.
        GameObject logo = new GameObject("logo", new Vector2d(10, 9 * (vp.getHeight() / 10)))
                .setType(GameObject.GOType.IMAGE)
                .setLayer(1)
                .setPriority(1)
                .setRelativeToCamera(true);
        add(logo);

        // add Title
        double gtx = vp.width / 4.0;
        double gty = (vp.height / 5.0) * 2.0;
        Font mainTitleFont = titleFont.deriveFont(16.0f);
        TextObject gameTitle = new TextObject("gameTitle", new Vector2d(gtx, gty))
                .setText("Play The Platform Game")
                .setFont(mainTitleFont);
        gameTitle.setColor(Color.WHITE)
                .setPriority(1)
                .setLayer(1)
                .setRelativeToCamera(true);
        add(gameTitle);

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
    public void update(long dt) {
        super.update(dt);
        GameObject bckGo = getGameObject("background");
        if (bckGo.position.x < 0 || bckGo.position.x > bckGo.width - vp.width) {
            bckGo.acceleration.x *= -1;
        }
        if (bckGo.position.y < 0 || bckGo.position.y > bckGo.height - vp.height) {
            bckGo.acceleration.y *= -1;
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
