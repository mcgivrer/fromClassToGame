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
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import fr.snapgames.fromclasstogame.demo.entity.LifeObject;
import fr.snapgames.fromclasstogame.demo.entity.ScoreObject;
import fr.snapgames.fromclasstogame.core.gfx.renderer.InventoryRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.LifeRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.ScoreRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.TextValueRenderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

public class DemoScene extends AbstractScene {

    private static Logger logger = LoggerFactory.getLogger(DemoScene.class);

    private int score = 0;
    private int life = 5;

    public DemoScene(Game g) {
        super(g, "demo");
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
        generateEnemies(30);

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
        GameObject key = new GameObject("key", new Vector2d(0, 0))
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

    private void generateEnemies(int nb) throws UnknownResource {
        for (int i = 0; i < nb; i++) {
            GameObject e = new GameObject("enemy_" + i, new Vector2d(0, 0))
                    .setType(GameObject.GOType.IMAGE)
                    .setPosition(rand(0, game.getPhysicEngine().getWorld().width),
                            rand(0, game.getPhysicEngine().getWorld().height))
                    .setColor(Color.ORANGE)
                    .setImage(ResourceManager.getImage("images/tiles01.png:orangeBall"))
                    .setMaterial(DefaultMaterial.RUBBER.getMaterial())
                    .setMass(rand(-8, 13));
            add(e);
        }
    }

    private void randomizeEnemies() {
        find("enemy_").forEach(go -> go
                .setAcceleration(new Vector2d(20 + rand(-60, 40), 20 + rand(-60, 40)))
        );
    }

    @Override
    public void activate() {
        randomizeEnemies();
        this.score = 0;
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
    }


    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_G:
                game.getPhysicEngine().getWorld().gravity.y = -game.getPhysicEngine().getWorld().gravity.y;
                break;
            case KeyEvent.VK_TAB:
                try {
                    generateEnemies(10);
                } catch (UnknownResource ex) {
                    logger.error("unable to load resource", e);
                }
                break;
            case KeyEvent.VK_MINUS:
                objectsList.stream().filter(c -> {
                    return c.name.startsWith("enemy_");
                }).collect(Collectors.toList()).forEach(o -> {
                    SystemManager.remove(o);
                });
                break;
        }
    }

    @Override
    public void render() {
        // if something >new< must be computed at render time ?

    }

}
