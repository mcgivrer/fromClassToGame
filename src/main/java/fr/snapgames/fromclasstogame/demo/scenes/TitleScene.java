package fr.snapgames.fromclasstogame.demo.scenes;

import fr.snapgames.fromclasstogame.core.Game;
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

    public TitleScene(Game g) {
        super(g, "title");
    }

    @Override
    public void initialize(Game g) {
        super.initialize(g);
        // font for title and text display
        titleFont = ResourceManager.getFont("fonts/FreeFont.ttf");
        // Background image resource
        bckImage = ResourceManager.getSlicedImage("images/backgrounds/volcano.png", "background", 0, 0, 1008, 642);

    }

    @Override
    public void create(Game g) throws UnknownResource {
        super.create(g);
        // Define the camera following the player object.
        Dimension vp = new Dimension(g.getRender().getBuffer().getWidth(), g.getRender().getBuffer().getHeight());

        // add a background image
        GameObject bckG = new GameObject("background", Vector2d.ZERO)
                .setImage(ResourceManager.getImage("images/backgrounds/volcano.png:background"))
                .setType(GameObject.GOType.IMAGE)
                .setLayer(100)
                .setPriority(100);
        add(bckG);

        // add Title
        double gtx = vp.width / 2.0;
        double gty = vp.height / 2.0;
        TextObject gameTitle = new TextObject("gameTitle", new Vector2d(gtx, gty)).setText("Play The Platform Game").setFont(titleFont);
        gameTitle.setColor(Color.WHITE);
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
