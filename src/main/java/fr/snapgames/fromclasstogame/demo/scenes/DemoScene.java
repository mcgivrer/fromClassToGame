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
import fr.snapgames.fromclasstogame.core.behaviors.InventorySelectorBehavior;
import fr.snapgames.fromclasstogame.core.entity.InventoryObject;
import fr.snapgames.fromclasstogame.demo.entity.LifeObject;
import fr.snapgames.fromclasstogame.demo.entity.ScoreObject;
import fr.snapgames.fromclasstogame.core.gfx.renderer.InventoryRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.LifeRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.ScoreRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.TextValueRenderHelper;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class DemoScene extends AbstractScene {

    private int score = 0;
    private int life = 5;

    public DemoScene(Game g) {
        super(g,"demo");
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
        // inventory
        ResourceManager.getSlicedImage("images/tiles01.png", "inventory_selector", 5 * 16, 3 * 16, 17, 16);
        ResourceManager.getSlicedImage("images/tiles01.png", "inventory_selected", 6 * 16, 3 * 16, 17, 16);
        // inventory objects item.
        ResourceManager.getSlicedImage("images/tiles01.png", "key", 21, 18, 8, 12);

        // Add a specific Render for the new ScoreObject
        g.getRender().addRenderHelper(new ScoreRenderHelper());
        g.getRender().addRenderHelper(new TextValueRenderHelper());
        g.getRender().addRenderHelper(new LifeRenderHelper());
        g.getRender().addRenderHelper(new InventoryRenderHelper());

        // load resources
    }

    @Override
    public void create(Game g) throws UnknownResource {
        g.setWorld(new World(800, 600));
        // add main character (player)
        GameObject player = new GameObject("player", new Vector2d(160, 100))
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
        generateEnemies();

        // add score display.
        ScoreObject scoreTO = (ScoreObject) new ScoreObject("score", 10, 4)
                .setScore(score)
                .relativeToCamera(true)
                .setLayer(1)
                .setColor(Color.WHITE)
                .setPriority(10);
        add(scoreTO);

        LifeObject lifeTO = (LifeObject) new LifeObject("life", 280, 4)
                .setLive(life)
                .relativeToCamera(true);
        add(lifeTO);

        BufferedImage keyImg = ResourceManager.getImage("images/tiles01.png:key");
        GameObject key = new GameObject("key", new Vector2d(0,0))
                .setImage(keyImg)
                .addAttribute("inventory", keyImg);

        InventoryObject inventory = (InventoryObject) new InventoryObject("inventory",
                new Vector2d(vp.getWidth() - 2, vp.getHeight() - 4))
                .setNbPlace(4)
                .setSelectedIndex(1)
                .relativeToCamera(true)
                .add(new InventorySelectorBehavior());

        // add a first object (a key !)
        inventory.add(key);
        add(inventory);

        randomizeEnemies();
    }

    private void generateEnemies() throws UnknownResource {
        for (int i = 0; i < 10; i++) {
            GameObject e = new GameObject("enemy_" + i, new Vector2d(0, 0))
                    .setType(GameObject.GOType.IMAGE)
                    .setColor(Color.ORANGE)
                    .setImage(ResourceManager.getImage("images/tiles01.png:orangeBall"))
                    .setMaterial(DefaultMaterial.RUBBER.getMaterial())
                    .setMass(rand(-8, 13));
            add(e);
        }
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
        super.input(inputHandler);
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
