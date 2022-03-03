package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.entity.Entity;
import fr.snapgames.fromclasstogame.core.entity.GameObject;

import java.awt.*;
import java.util.List;

public class OnEntityCollision implements ProcessBehavior<Entity> {
    @Override
    public void process(List<Entity> objects) {

        GameObject o1 = (GameObject) objects.get(0);
        GameObject o2 = (GameObject) objects.get(1);
        o1.setDebugColor(Color.RED);
        o2.setDebugColor(Color.ORANGE);

        o1.setCollide(true);
    }
}
