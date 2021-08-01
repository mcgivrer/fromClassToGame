package fr.snapgames.fromclasstogame.test.scenes;

import java.util.List;

import fr.snapgames.fromclasstogame.Game;
import fr.snapgames.fromclasstogame.GameObject;
import fr.snapgames.fromclasstogame.Scene;

public class TestScene implements Scene {

    @Override
    public String getName() {

        return "test";
    }

    @Override
    public void initialize(Game g) {

    }

    @Override
    public void create(Game g) {

    }

    @Override
    public void add(GameObject go) {

    }

    @Override
    public GameObject getGameObject(String name) {
        return null;
    }

    @Override
    public List<GameObject> find(String filteredName) {
        return null;
    }

    @Override
    public List<GameObject> getObjectsList() {
        return null;
    }

    @Override
    public void activate() {

    }

    @Override
    public void update(long dt) {

    }

    @Override
    public void input() {

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }

}
