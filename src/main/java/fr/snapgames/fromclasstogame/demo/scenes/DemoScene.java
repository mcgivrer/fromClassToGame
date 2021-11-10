package fr.snapgames.fromclasstogame.demo.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.renderer.InventoryRenderHelper;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.physic.Material;
import fr.snapgames.fromclasstogame.core.physic.Material.DefaultMaterial;
import fr.snapgames.fromclasstogame.core.physic.Utils;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.physic.World;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import fr.snapgames.fromclasstogame.demo.behaviors.DebugSwitcherBehavior;
import fr.snapgames.fromclasstogame.demo.behaviors.InventorySelectorBehavior;
import fr.snapgames.fromclasstogame.demo.behaviors.PlayerActionBehavior;
import fr.snapgames.fromclasstogame.demo.entity.InventoryObject;
import fr.snapgames.fromclasstogame.demo.entity.LifeObject;
import fr.snapgames.fromclasstogame.demo.entity.ScoreObject;
import fr.snapgames.fromclasstogame.demo.render.LifeRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.ScoreRenderHelper;
import fr.snapgames.fromclasstogame.demo.render.TextValueRenderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Demo Scene to test features during framework development.
 *
 * @author Frédéric Delorme
 * @since 0.0.1
 */
public class DemoScene extends AbstractScene {

    private static final Logger logger = LoggerFactory.getLogger(DemoScene.class);

    private int score = 0;
    private int life = 5;

    /**
     * Create the Demo.
     *
     * @param g the parent Game object.
     */
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
        // inventory selector states
        ResourceManager.getSlicedImage("images/tiles01.png", "inventory_selector", 5 * 16, 3 * 16, 17, 16);
        ResourceManager.getSlicedImage("images/tiles01.png", "inventory_selected", 6 * 16, 3 * 16, 17, 16);
        // inventory objects item.
        ResourceManager.getSlicedImage("images/tiles01.png", "key", 21, 18, 8, 12);
        // Background image resource
        ResourceManager.getSlicedImage("images/backgrounds/volcano.png", "background", 0, 0, 1008, 642);

        // Add a specific Render for the new ScoreObject
        g.getRender().addRenderHelper(new ScoreRenderHelper());
        g.getRender().addRenderHelper(new TextValueRenderHelper());
        g.getRender().addRenderHelper(new LifeRenderHelper());
        g.getRender().addRenderHelper(new InventoryRenderHelper());
    }

    @Override
    public void create(Game g) throws UnknownResource {
        g.setWorld(new World(800, 600));
        // add main character (player)
        Material m = DefaultMaterial.newMaterial("player", 0.25, 0.3, 0.96, 0.997);
        GameObject player = new GameObject("player", new Vector2d(160, 100))
                .setType(GameObject.GOType.IMAGE)
                .setColor(Color.RED)
                .setLayer(1)
                .setPriority(0)
                .setImage(ResourceManager.getImage("images/tiles01.png:player"))
                .setMaterial(m)
                .setMass(10)
                .setDebug(1)
                .addAttribute("jumping", false)
                .addAttribute("accelStep", 10.0)
                .addAttribute("jumpAccel", -20.0)
                .addAttribute("maxHorizontalVelocity", 20.0)
                .addAttribute("maxVerticalVelocity", 30.0)
                .add(new PlayerActionBehavior());
        add(player);

        // Define the camera following the player object.
        Dimension vp = new Dimension(g.getRender().getBuffer().getWidth(), g.getRender().getBuffer().getHeight());
        Camera camera = new Camera("cam01")
                .setTarget(player)
                .setTweenFactor(0.02)
                .setViewport(vp);
        add(camera);

        // Add enemies(enemy_99)
        generateEnemies(10);


        /**
         * This is commented only for easiest development purpose, must be uncommented.
         * ---
         * // add a background image
         * GameObject bckG = new GameObject("background", Vector2d.ZERO)
         *   .setImage(ResourceManager.getImage("images/backgrounds/volcano.png:background"))
         *   .setType(GameObject.GOType.IMAGE)
         *   .setLayer(100)
         *   .setPriority(100);
         * add(bckG);
         * ---
         */

        // add score display.
        ScoreObject scoreTO = (ScoreObject) new ScoreObject("score", new Vector2d(10, 4)).setScore(score).relativeToCamera(true)
                .setLayer(1).setColor(Color.WHITE).setPriority(10);
        add(scoreTO);

        LifeObject lifeTO = (LifeObject) new LifeObject("life", new Vector2d(280, 4)).setLive(life).relativeToCamera(true);
        add(lifeTO);

        // prepare the inventory item image
        BufferedImage keyItemImg = ResourceManager.getImage("images/tiles01.png:key");
        // create the Key Item object
        GameObject keyItem = new GameObject("key", new Vector2d(0, 0))
                .setImage(keyItemImg)
                .addAttribute("inventory", keyItemImg);
        // create the Inventory to store the created item
        InventoryObject inventory = (InventoryObject) new InventoryObject("inventory",
                new Vector2d(vp.getWidth() - 2, vp.getHeight() - 4))
                .setNbPlace(4)
                .setSelectedIndex(1)
                .relativeToCamera(true)
                .add(new InventorySelectorBehavior());
        // add a first object (a key !)
        inventory.add(keyItem);
        add(inventory);

        // shuffle `enemy_*`'s object's position and acceleration
        randomizeFilteredGameObject("enemy_");

        // Add the Debug switcher capability to this scene
        addBehavior(new DebugSwitcherBehavior());

    }

    private void generateEnemies(int nbEnemies) throws UnknownResource {
        for (int i = 0; i < nbEnemies; i++) {
            GameObject e = new GameObject("enemy_" + GameObject.getIndex(), new Vector2d(0, 0))
                    .setType(GameObject.GOType.IMAGE)
                    .setPosition(Utils.rand(0, game.getPhysicEngine().getWorld().width),
                            Utils.rand(0, game.getPhysicEngine().getWorld().height))
                    .setColor(Color.ORANGE).setImage(ResourceManager.getImage("images/tiles01.png:orangeBall"))
                    .setMaterial(DefaultMaterial.RUBBER.getMaterial()).setMass(Utils.rand(-8, 13)).setLayer(10)
                    .setPriority(3);
            randomizePosAndAccGameObject(e);
            add(e);
        }
    }

    private synchronized void removeEnemies(int nbEnemiesToRemove) {
        List<GameObject> obj = find("enemy_");
        for (int i = 0; i < nbEnemiesToRemove; i++) {
            GameObject o = obj.get(i);
            remove(o);
        }
    }

    @Override
    public void activate() {
        randomizeFilteredGameObject("enemy_");
        this.score = 0;
    }

    private synchronized void randomizeFilteredGameObject(String rootName) {
        find(rootName).forEach(go -> randomizePosAndAccGameObject(go));
    }

    private GameObject randomizePosAndAccGameObject(GameObject go) {
        return go
                .setPosition(Utils.randV2d(0, game.getPhysicEngine().getWorld().width, 0, game.getPhysicEngine().getWorld().height))
                .setAcceleration(Utils.randV2d(-40, 40, 0, 0));
    }

    private GameObject randomizeAccelerationAndFrictionAndBounciness(
            GameObject go,
            double accFactorX,
            double accFactorY,
            double dynFriction,
            double bounciness) {
        Material m = go.material;
        m.dynFriction = Utils.rand(dynFriction - 0.1, dynFriction + 0.1);
        m.bounciness = Utils.rand(bounciness - 0.1, bounciness + 0.1);
        return go.setAcceleration(Utils.randV2d(-accFactorX, accFactorX, -accFactorY, accFactorY));
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
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
                find("enemy_").forEach(o -> {
                    randomizeAccelerationAndFrictionAndBounciness(o, 100, 100, 0.98, 0.6);
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        ActionHandler ah = (ActionHandler) SystemManager.get(ActionHandler.class);
        int nbEnemies = 10;
        if (ah.getCtrl()) {
            nbEnemies = 50;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_PAGE_UP:
                try {
                    generateEnemies(nbEnemies);
                } catch (UnknownResource ex) {
                    logger.error("Unable to generate enemies", ex);
                }
                break;
            case KeyEvent.VK_PAGE_DOWN:
                removeEnemies(nbEnemies);
                break;
            case KeyEvent.VK_S:
                find("enemy_").forEach(o -> {
                    randomizeAccelerationAndFrictionAndBounciness(o, 100, 100, 0.98, 0.6);
                });
                break;
            case KeyEvent.VK_G:
                Vector2d g = game.getPhysicEngine().getWorld().gravity.multiply(-1);
                game.getPhysicEngine().getWorld().setGravity(g);
                break;
            default:
                break;
        }
    }

}
