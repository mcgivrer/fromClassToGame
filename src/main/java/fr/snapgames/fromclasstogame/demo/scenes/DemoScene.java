package fr.snapgames.fromclasstogame.demo.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.CopyObjectPosition;
import fr.snapgames.fromclasstogame.core.behaviors.PlayerActionBehavior;
import fr.snapgames.fromclasstogame.core.behaviors.particle.FireParticleBehavior;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.DebugViewportGrid;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.entity.particles.ParticleSystem;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.renderer.ParticleSystemRenderHelper;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.*;
import fr.snapgames.fromclasstogame.core.physic.Material.DefaultMaterial;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.core.system.SystemManager;
import fr.snapgames.fromclasstogame.demo.behaviors.InventorySelectorBehavior;
import fr.snapgames.fromclasstogame.demo.entity.InventoryObject;
import fr.snapgames.fromclasstogame.demo.entity.LifeObject;
import fr.snapgames.fromclasstogame.demo.entity.ScoreObject;
import fr.snapgames.fromclasstogame.demo.render.InventoryRenderHelper;
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
        ResourceManager.getSlicedImage("images/tiles01.png", "potion", 34, 18, 14, 15);
        // Background image resource
        ResourceManager.getSlicedImage("images/backgrounds/volcano.png", "background", 0, 0, 1008, 642);

        // Add a specific Render for the new GameObject implementation for
        // - ScoreObject
        g.getRender().addRenderHelper(new ScoreRenderHelper(g.getRender()));
        // - TestValue
        g.getRender().addRenderHelper(new TextValueRenderHelper(g.getRender()));
        // - LifeObject
        g.getRender().addRenderHelper(new LifeRenderHelper(g.getRender()));
        // - InventoryObject
        g.getRender().addRenderHelper(new InventoryRenderHelper(g.getRender()));
        // - ParticleSystem
        g.getRender().addRenderHelper(new ParticleSystemRenderHelper(g.getRender()));
    }

    @Override
    public void create(Game g) throws UnknownResource {
        super.create(g);
        // Declare World playground
        World world = new World(800, 600);
        // create a basic wind all over the play area
        InfluenceArea2d iArea = new InfluenceArea2d(
                new Vector2d(0.475, 0.0),
                new BoundingBox(new Vector2d(0.0, 0.0), world.width, world.height,
                        BoundingBox.BoundingBoxType.RECTANGLE),
                3);
        world.addInfluenceArea(iArea);
        g.setWorld(world);

        // add Viewport Grid debug view
        DebugViewportGrid dvg = new DebugViewportGrid("vpgrid", world, 32, 32);
        dvg.setDebug(1);
        dvg.setLayer(11);
        dvg.setPriority(2);
        add(dvg);

        // add main character (player)
        Material m = DefaultMaterial.newMaterial("playerMaterial", 0.25, 0.3, 0.80, 0.98);
        GameObject player = new GameObject("player", new Vector2d(160, 100))
                .setType(GameObject.GOType.IMAGE)
                .setColor(Color.RED)
                .setLayer(1)
                .setPriority(2)
                .setImage(ResourceManager.getImage("images/tiles01.png:player"))
                .setMaterial(m)
                .setMass(10)
                .setDebug(3)
                .addAttribute("jumping", false)
                .addAttribute("accelStep", 10.0)
                .addAttribute("jumpAccel", -20.0)
                .addAttribute("maxHorizontalVelocity", 20.0)
                .addAttribute("maxVerticalVelocity", 30.0)
                .addAttribute("score", 0)
                .addAttribute("lifes", 5)
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

        // add a background image
        GameObject bckG = new GameObject("background", Vector2d.ZERO)
                .setImage(ResourceManager.getImage("images/backgrounds/volcano.png:background"))
                .setType(GameObject.GOType.IMAGE)
                .setLayer(100)
                .setPriority(100);
        add(bckG);

        // add a ParticleSystem
        ParticleSystem ps = new ParticleSystem("PS_test", player.position);
        ps.addParticleBehavior(
                        new FireParticleBehavior(ps, 1200, true)
                                .setColor(Color.YELLOW))
                .create(10)
                .setFeeding(2)
                .setEmitFrequency(1200)
                .add(new CopyObjectPosition(player, new Vector2d(7, -4)))
                .setDebug(4)
                .setLayer(1)
                .setDebugOffset(-100, -100)
                .setPriority(1);
        add(ps);

        // add score display.
        int score = (int) (player.getAttribute("score", 0));
        ScoreObject scoreTO = (ScoreObject) new ScoreObject(
                "score",
                new Vector2d(10, 4))
                .setScore(score)
                .setRelativeToCamera(true)
                .setLayer(1)
                .setColor(Color.WHITE)
                .setPriority(10);
        add(scoreTO);

        // Add a Life display
        int life = (int) player.getAttribute("lifes", 0);

        LifeObject lifeTO = (LifeObject) new LifeObject("life", new Vector2d(280, 4)).setLive(life).setRelativeToCamera(true);
        add(lifeTO);

        // prepare the inventory item image
        BufferedImage keyItemImg = ResourceManager.getImage("images/tiles01.png:key");
        BufferedImage potionItemImg = ResourceManager.getImage("images/tiles01.png:potion");
        // create the Key Item object
        GameObject keyItem = new GameObject("key", new Vector2d(0, 0))
                .setImage(keyItemImg)
                .addAttribute("inventory", keyItemImg);
        // create the Key Item object
        GameObject potionItem = new GameObject("potion", new Vector2d(0, 0))
                .setImage(potionItemImg)
                .addAttribute("inventory", potionItemImg);
        // create the Inventory to store the created item
        InventoryObject inventory = (InventoryObject) new InventoryObject("inventory",
                new Vector2d(vp.getWidth() - 2, vp.getHeight() - 4))
                .setNbPlace(6)
                .setSelectedIndex(1)
                .setRelativeToCamera(true)
                .add(new InventorySelectorBehavior());
        // add a first object (a key !)
        inventory.add(keyItem);
        inventory.add(potionItem);
        add(inventory);

        // Shuffle `enemy_*`'s object's position and acceleration
        randomizeFilteredGameObject("enemy_");

        // Welcome text at middle bottom center game screen
        double tPosX = game.getRender().getBuffer().getWidth() / 3.0;
        double tPosY = (game.getRender().getBuffer().getHeight() / 5.0) * 4.0;
        Font welcomeFont = ResourceManager.getFont("./fonts/FreePixel.ttf").deriveFont(11.0f);
        TextObject welcome = new TextObject("welcomeMsg", new Vector2d(tPosX, tPosY))
                .setText("Welcome on Board").setFont(welcomeFont);
        welcome.setDuration(50000).setLayer(0).setPriority(1).setRelativeToCamera(true).setDebug(3);
        add(welcome);


    }

    /**
     * Generate a random set of nbEnemies on screen?
     *
     * @param nbEnemies
     * @throws UnknownResource
     */
    private void generateEnemies(int nbEnemies) throws UnknownResource {
        for (int i = 0; i < nbEnemies; i++) {
            GameObject e = new GameObject("enemy_" + GameObject.getIndex(), new Vector2d(0, 0))
                    .setType(GameObject.GOType.IMAGE)
                    .setPosition(Utils.rand(0, game.getPhysicEngine().getWorld().width),
                            Utils.rand(0, game.getPhysicEngine().getWorld().height))
                    .setColor(Color.ORANGE).setImage(ResourceManager.getImage("images/tiles01.png:orangeBall"))
                    .setMaterial(DefaultMaterial.RUBBER.getMaterial()).setMass(Utils.rand(-8, 13)).setLayer(10)
                    .setPriority(3)
                    .setSize(8, 8);
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
        randomizeFilteredGameObject("player");
        objects.get("player").addAttribute("score", 0);
        objects.get("welcomeMsg").setDuration(5000).active = true;

    }

    private synchronized void randomizeFilteredGameObject(String rootName) {
        find(rootName).forEach(this::randomizePosAndAccGameObject);
    }

    private GameObject randomizePosAndAccGameObject(GameObject go) {
        return go
                .setPosition(Utils.randV2d(
                        0, game.getPhysicEngine().getWorld().width,
                        0, game.getPhysicEngine().getWorld().height))
                .setAcceleration(Utils.randV2d(-40, 40, 0, 0))
                .setGravity(game.getPhysicEngine().getWorld().gravity);
    }

    /**
     * generate randomly {@link Material#dynFriction}, {@link Material#bounciness} and {@link GameObject#acceleration}
     * into some max values for the {@link GameObject} <code>go</code>.
     *
     * @param go          the GameObject to be randomly updated
     * @param accFactorX  the +/- range factor for X acceleration
     * @param accFactorY  the +/- range factor for Y acceleration
     * @param dynFriction dynamic friction for the {@link GameObject#material}
     * @param bounciness  bounciness factor for the {@link GameObject#material}
     * @return the modified GameObject
     */
    private GameObject randomizeAccelerationAndFrictionAndBounciness(
            GameObject go,
            double accFactorX,
            double accFactorY,
            double dynFriction,
            double bounciness) {
        Material m = go.material;
        m.dynFriction = Utils.rand(dynFriction - 0.1, dynFriction + 0.1);
        m.bounciness = Utils.rand(bounciness - 0.1, bounciness + 0.1);
        go.forces.add(Utils.randV2d(-accFactorX, accFactorX, -accFactorY, accFactorY));
        return go;
    }

    @Override
    public void update(long dt) {

        super.update(dt);
        GameObject player = objects.get("player");
        int score = (int) player.getAttribute("score", 0);
        ScoreObject scoreTO = (ScoreObject) objects.get("score");
        score++;
        scoreTO.setScore(score);
        player.addAttribute("score", score);
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
                find("enemy_").forEach(o -> randomizeAccelerationAndFrictionAndBounciness(o, 100, 100, 0.98, 0.6));
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        // Getting the ActionHandler to access any input states.
        ActionHandler ah = (ActionHandler) SystemManager.get(ActionHandler.class);
        int nbEnemies = getNbEnemiesToAdd(ah);

        switch (e.getKeyCode()) {

            case KeyEvent.VK_PAGE_UP:
                // Create new enemies
                try {
                    generateEnemies(nbEnemies);
                } catch (UnknownResource ex) {
                    logger.error("Unable to generate enemies", ex);
                }
                break;

            case KeyEvent.VK_PAGE_DOWN:
                // remove some enemies
                removeEnemies(nbEnemies);
                break;

            case KeyEvent.VK_S:
                // shake all the objects named "enemy_"
                find("enemy_").forEach(o -> randomizeAccelerationAndFrictionAndBounciness(o, 100, 100, 0.98, 0.6));
                break;

            case KeyEvent.VK_F:
                // switch Particles system on or off
                find("PS_").forEach(o -> o.active = !o.active);
                break;

            case KeyEvent.VK_G:
                // inverse Gravity on this world !
                World world = ((PhysicEngine) SystemManager.get(PhysicEngine.class)).getWorld();
                if (world != null) {
                    world.gravity.multiply(-1);
                }
                break;

            default:
                break;
        }
    }

    /**
     * Compute the number of enemies to generate according to the CTRL press status.
     *
     * @param ah the ActionHandler
     * @return the number to be generated.
     */
    private int getNbEnemiesToAdd(ActionHandler ah) {
        int nbEnemies = 10;
        if (ah != null && ah.getCtrl()) {
            nbEnemies = 50;
        }
        return nbEnemies;
    }

}
