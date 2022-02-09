package fr.snapgames.fromclasstogame.core.physic.collision;

import fr.snapgames.fromclasstogame.core.behaviors.OnEntityCollision;
import fr.snapgames.fromclasstogame.core.behaviors.ProcessBehavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Collision {
    private final GameObject object1;
    private final GameObject object2;

    public Collision(GameObject o1, GameObject o2) {
        this.object1 = o1;
        this.object2 = o2;
    }

    public GameObject getObject1() {
        return object1;
    }

    public GameObject getObject2() {
        return object2;
    }

    public void process(ProcessBehavior<GameObject> oec) {
        oec.process(Arrays.asList(object1, object2));
    }
}
