package fr.snapgames.fromclasstogame.demo.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.InputHandler;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.physic.Material.DefaultMaterial;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.physic.World;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.demo.entity.LifeObject;
import fr.snapgames.fromclasstogame.demo.entity.ScoreObject;
import fr.snapgames.fromclasstogame.demo.entity.TextValueObject;
import fr.snapgames.fromclasstogame.demo.render.LifeRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.ScoreRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.TextValueRenderHelper;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DemoScene extends AbstractScene {

    private int score = 0;
    private int life = 5;

    public DemoScene(Game g) {
        super(g);
    }

    @Override
    public String getName() {
        return "demo";
    }

    @Override
    public void initialize(Game g) {
        super.initialize(g);
        // Load resources
        ResourceManager.getFont("fonts/FreePixel.ttf");
        ResourceManager.getSlicedImage("images/tiles01.png", "heart", 0, 16, 16, 16);
        ResourceManager.getSlicedImage("images/tiles01.png", "*", 0, 0, 16, 16);
        ResourceManager.getSlicedImage("images/tiles01.png", "player", 8 * 16, 48, 16, 16);
        ResourceManager.getSlicedImage("images/tiles01.png", "orangeBall", 9 * 16, 48, 16, 16);

        // Add a specific Render for the new ScoreObject
        g.getRender().addRenderHelper(new ScoreRenderHelper());
        g.getRender().addRenderHelper(new TextValueRenderHelper());
        g.getRender().addRenderHelper(new LifeRenderHelper());

        // load resources
    }

    @Override
    public void create(Game g) throws UnknownResource {
        g.setWorld(new World(800, 600));
        // add main character (player)
        GameObject player = new GameObject("player", 160, 100)
                .setType(GameObject.GOType.IMAGE)
                .setColor(Color.RED)
                .setImage(ResourceManager.getImage("images/tiles01.png:player"))
                .setMaterial(DefaultMaterial.WOOD.getMaterial())
                .setMass(10);
        add(player);

        Dimension vp = new Dimension(g.getRender().getBuffer().getWidth(), g.getRender().getBuffer().getHeight());
        Camera camera = new Camera("cam01").setTarget(player).setTweenFactor(0.02).setViewport(vp);
        add(camera);

        // Add enemies(enemy_99)
        for (int i = 0; i < 10; i++) {
            GameObject e = new GameObject("enemy_" + i, 0, 0)
                    .setType(GameObject.GOType.IMAGE)
                    .setColor(Color.ORANGE)
                    .setImage(ResourceManager.getImage("images/tiles01.png:orangeBall"))
                    .setMaterial(DefaultMaterial.RUBBER.getMaterial())
                    .setMass(rand(-8, 13));
            add(e);
        }
        Font f = ResourceManager.getFont("fonts/FreePixel.ttf").deriveFont(Font.BOLD, 14);

        // add score display.
        ScoreObject scoreTO = (ScoreObject) new ScoreObject("score", 10, 4).setScore(score).setFont(f)
                .relativeToCamera(true).setLayer(1);
        scoreTO.setColor(Color.WHITE);
        scoreTO.priority = 10;
        add(scoreTO);

        LifeObject lifeTO = (LifeObject) new LifeObject("life", 280, 4).setLive(5).relativeToCamera(true);
        add(lifeTO);

        randomizeEnemies();
    }

    @Override
    public void activate() {
        randomizeEnemies();
        this.score = 0;
    }

    private void randomizeEnemies() {
        find("enemy_").forEach(go -> go
                .setPosition(rand(0, game.getPhysicEngine().getWorld().width),
                        rand(0, game.getPhysicEngine().getWorld().height))
                .setAcceleration(new Vector2d(rand(-40, 40), 0.0))
        );
    }

    @Override
    public void update(long dt) {

        super.update(dt);
        ScoreObject scoreTO = (ScoreObject) objects.get("score");
        score++;
        scoreTO.setScore(score);
    }

    @Override
    public void dispose() {
        objects.clear();
        objectsList.clear();
    }

    @Override
    public void input(InputHandler inputHandler) {
        GameObject player = this.getGameObject("player");
        double speed = 0.0;
        double speedStep = 2;
        if (inputHandler.getKey(KeyEvent.VK_CONTROL)) {
            speed = speedStep * 4;
        } else if (inputHandler.getKey(KeyEvent.VK_SHIFT)) {
            speed = speedStep * 2;
        } else {
            speed = speedStep;
        }

        if (inputHandler.getKey(KeyEvent.VK_UP)) {
            player.acceleration.y = -14 * speed;
        }
        if (inputHandler.getKey(KeyEvent.VK_DOWN)) {
            player.acceleration.y = speed;
        }
        if (inputHandler.getKey(KeyEvent.VK_LEFT)) {
            player.acceleration.x = -speed;
        }
        if (inputHandler.getKey(KeyEvent.VK_RIGHT)) {
            player.acceleration.x = speed;
        }
        if (inputHandler.getKey(KeyEvent.VK_G)) {
            game.getPhysicEngine().getWorld().gravity.y = -game.getPhysicEngine().getWorld().gravity.y;
        }
    }

    @Override
    public void render() {
        // if something >new< must be computed at render time ?

    }

}
