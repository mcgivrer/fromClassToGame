package fr.snapgames.fromclasstogame.scenes;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.snapgames.fromclasstogame.Game;
import fr.snapgames.fromclasstogame.GameObject;
import fr.snapgames.fromclasstogame.ResourceManager;
import fr.snapgames.fromclasstogame.Scene;
import fr.snapgames.fromclasstogame.TextObject;

public class DemoScene implements Scene {

    private Map<String, GameObject> objects = new HashMap<>();
    private List<GameObject> objectsList = new ArrayList<>();

    private Game game;

    private int score = 0;

    @Override
    public String getName() {
        return "demo";
    }

    @Override
    public void initialize(Game g) {
        this.game = g;
        ResourceManager.getFont("fonts/FreePixel.ttf");
        ResourceManager.getSlicedImage("images/tiles.png", "player", 0, 0, 16, 16);
        ResourceManager.getSlicedImage("images/tiles.png", "orangeBall", 16, 0, 16, 16);
    }

    @Override
    public void create(Game g) {
        // add main character (player)
        GameObject player = new GameObject("player", 160, 100).setColor(Color.RED).setSpeed(0.02, 0.02)
                .setSize(16.0, 16.0).setImage(ResourceManager.getImage("images/tiles.png:player"));
        add(player);
        // Add enemies(enemy_99)
        for (int i = 0; i < 10; i++) {
            GameObject e = new GameObject("enemy_" + i, rand(0, 320), rand(0, 200))
                    .setSpeed(rand(-0.05, 0.05), rand(-0.05, 0.05)).setColor(Color.ORANGE).setSize(8, 8)
                    .setImage(ResourceManager.getImage("images/tiles.png:orangeBall"));
            add(e);
        }
        Font f = ResourceManager.getFont("fonts/FreePixel.ttf").deriveFont(Font.CENTER_BASELINE, 14);
        // add some fixed text.
        TextObject scoreTO = new TextObject("score", 10, 20).setText("00000").setFont(f);
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

        objectsList.forEach(e->{
            e.update(dt);
        });
        TextObject scoreTO = (TextObject) objects.get("score");
        scoreTO.setText(String.format("%05d", score));
        score++;
    }

    @Override
    public void input() {

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {
        objects.clear();
        objectsList.clear();

    }

    public void add(GameObject go) {
        if (!objects.containsKey(go.name)) {
            objects.put(go.name, go);
            objectsList.add(go);
            game.getRender().add(go);
        }
    }

    public GameObject getGameObject(String name) {
        return objects.get(name);
    }

    /**
     * find GameObject filtered on their name according to a filteredName.
     * 
     * @param filteredName
     * @return
     */
    public List<GameObject> find(String filteredName) {
        return objectsList.stream().filter(o -> o.name.contains(filteredName)).collect(Collectors.toList());
    }

    public List<GameObject> getObjectsList() {
        return objectsList;
    }

    public double rand(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}
