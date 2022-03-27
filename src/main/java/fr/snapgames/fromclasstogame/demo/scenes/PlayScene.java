package fr.snapgames.fromclasstogame.demo.scenes;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.behaviors.DebugSwitcherBehavior;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.DebugViewportGrid;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileMap;
import fr.snapgames.fromclasstogame.core.entity.tilemap.UnkownGameObjectException;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.gfx.renderer.ParticleSystemRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.TileMapRenderHelper;
import fr.snapgames.fromclasstogame.core.io.LevelLoader;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.physic.InfluenceArea2d;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.physic.World;
import fr.snapgames.fromclasstogame.core.physic.collision.BoundingBox;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
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
import java.awt.image.BufferedImage;

public class PlayScene extends AbstractScene {

    private static final Logger logger = LoggerFactory.getLogger(PlayScene.class);


    // Tilemap loader, convert level file into a TileMap,TileSet,Tile's, and GameObejct's.
    LevelLoader tmLoader;

    public PlayScene(Game g) {
        super(g, "play");
    }

    @Override
    public void initialize(Game g) {
        super.initialize(g);
        if (g.getConfiguration().debugLevel > 0) {
            // Add the Debug switcher capability to this scene
            addBehavior(new DebugSwitcherBehavior());
        }

        // Load resources
        ResourceManager.getFont("fonts/FreePixel.ttf");
        ResourceManager.getSlicedImage("images/tiles01.png", "heart", 0, 16, 16, 16);
        ResourceManager.getSlicedImage("images/tiles01.png", "*", 0, 0, 16, 16);
        // inventory selector states
        ResourceManager.getSlicedImage("images/tiles01.png", "inventory_selector", 5 * 16, 3 * 16, 17, 16);
        ResourceManager.getSlicedImage("images/tiles01.png", "inventory_selected", 6 * 16, 3 * 16, 17, 16);
        // inventory objects item.
        ResourceManager.getSlicedImage("images/tiles01.png", "key", 21, 18, 8, 12);
        ResourceManager.getSlicedImage("images/tiles01.png", "potion", 34, 18, 14, 15);
        // Background image resource
        ResourceManager.getSlicedImage("images/backgrounds/volcano.png", "background", 0, 0, 1008, 642);

        // Add a specific Render for the new ScoreObject
        Renderer renderer = g.getRenderer();
        renderer.addRenderHelper(new TileMapRenderHelper(renderer));
        // Add a specific Render for the new GameObject implementation for
        // - ScoreObject
        renderer.addRenderHelper(new ScoreRenderHelper(renderer));
        // - TestValue
        renderer.addRenderHelper(new TextValueRenderHelper(renderer));
        // - LifeObject
        renderer.addRenderHelper(new LifeRenderHelper(renderer));
        // - InventoryObject
        renderer.addRenderHelper(new InventoryRenderHelper(renderer));
        // - ParticleSystem
        renderer.addRenderHelper(new ParticleSystemRenderHelper(renderer));

        // Initialize the Tilemap loader
        tmLoader = new LevelLoader(game);
    }

    @Override
    public void create(Game g) throws UnknownResource {
        super.create(g);
        // Declare World playground
        World world = new World(800, 600);
        // create a basic wind all over the play area
        InfluenceArea2d iArea = new InfluenceArea2d(
                new Vector2d(0.475, 0),
                new BoundingBox(Vector2d.ZERO, 800, 600,
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


        // add a TileMap object
        TileMap tm = tmLoader.load(this, "./levels/lvl0101.properties");
        add(tm);

        try {

            GameObject player = tm.getObject("player");

            // Define the camera following the player object.
            Dimension vp = new Dimension(g.getRenderer().getBuffer().getWidth(), g.getRenderer().getBuffer().getHeight());
            Camera camera = new Camera("cam01")
                    .setTarget(player)
                    .setTweenFactor(0.02)
                    .setViewport(vp);
            add(camera);

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
        } catch (UnkownGameObjectException ugo) {
            logger.error("Unable to load level {} and initialize objects", tm.name, ugo);
        }

        // Welcome text at middle bottom center game screen
        double tPosX = game.getRenderer().getBuffer().getWidth() / 3.0;
        double tPosY = (game.getRenderer().getBuffer().getHeight() / 5.0) * 4.0;
        TextObject welcome = new TextObject("welcomeMsg", new Vector2d(tPosX, tPosY))
                .setText("Welcome on Board");
        welcome.setDuration(5000).setLayer(0).setPriority(1).setRelativeToCamera(true);
        add(welcome);


    }

    @Override
    public void dispose() {

    }
}
