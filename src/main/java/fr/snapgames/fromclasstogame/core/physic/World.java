package fr.snapgames.fromclasstogame.core.physic;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.entity.GameObject;

import java.util.ArrayList;
import java.util.List;

public class World {


    public double width;
    public double height;

    public double gravity = 0.981;

    public World(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public World setGravity(double g) {
        this.gravity = g;
        return this;
    }


}
