package fr.snapgames.fromclasstogame.demo.scenes;

import java.awt.*;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.entity.Camera;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.scenes.AbstractScene;
import fr.snapgames.fromclasstogame.demo.render.ScoreRenderHelper;

public class DemoScene extends AbstractScene {

    private int score = 0;

    @Override
    public String getName() {
        return "demo";
    }

    @Override
    public void initialize(Game g) {
        super.initialize(g);
        // Add a specific Render for the new ScoreObject
        g.getRender().addRenderHelper(new ScoreRenderHelper());

        // load resources
        ResourceManager.getFont("fonts/FreePixel.ttf");
        ResourceManager.getSlicedImage("images/tiles.png", "player", 0, 0, 16, 16);
        ResourceManager.getSlicedImage("images/tiles.png", "orangeBall", 16, 0, 16, 16);
    }

    @Override
    public void create(Game g) throws UnknownResource {
        // add main character (player)
        GameObject player = new GameObject("player", 160, 100).setColor(Color.RED).setSpeed(0.02, 0.02)
                .setSize(16.0, 16.0).setImage(ResourceManager.getImage("images/tiles.png:player"));
        add(player);

        Dimension vp = new Dimension(g.getRender().getBuffer().getWidth(),g.getRender().getBuffer().getHeight());
        Camera camera = new Camera("cam01").setTarget(player).setTweenFactor(0.05).setViewport( vp );
        add(camera);

        // Add enemies(enemy_99)
        for (int i = 0; i < 10; i++) {
            GameObject e = new GameObject("enemy_" + i, rand(0, 320), rand(0, 200))
                    .setSpeed(rand(-0.05, 0.05), rand(-0.05, 0.05)).setColor(Color.ORANGE).setSize(8, 8)
                    .setImage(ResourceManager.getImage("images/tiles.png:orangeBall"));
            add(e);
        }
        Font f = ResourceManager.getFont("fonts/FreePixel.ttf").deriveFont(Font.CENTER_BASELINE, 14);
        // add some fixed text.
        ScoreObject scoreTO = (ScoreObject) new ScoreObject("score", 10, 20).setScore(score).setFont(f);
        scoreTO.setColor(Color.WHITE);
        scoreTO.priority = 10;
        add(scoreTO);
    }

    @Override
    public void activate() {
        objects.get("player").setSpeed(0.02, 0.02).setPosition(160, 100);
        find("enemy_").forEach(go -> {
            go.setPosition(rand(0, 320), rand(0, 200));
            go.setSpeed(rand(-0.05, 0.05), rand(-0.05, 0.05));
        });
        this.score = 0;
    }

    @Override
    public void update(long dt) {

        super.update(dt);
        ScoreObject scoreTO = (ScoreObject) objects.get("score");
        scoreTO.setScore(score);
        score++;
    }

    @Override
    public void dispose() {
        objects.clear();
        objectsList.clear();
    }

    @Override
    public void input() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render() {
        // TODO Auto-generated method stub

    }

}
