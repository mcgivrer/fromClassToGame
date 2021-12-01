package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.physic.World;

import java.util.List;

public class DebugViewportGrid extends GameObject {
    public int gridX;
    public int gridY;
    private World w;


    public DebugViewportGrid(String objectName, World w) {
        super(objectName);
        this.w = w;
        this.gridX = 16;
        this.gridY = 16;
    }

    public DebugViewportGrid(String objectName, World w, int gridX, int gridY) {
        super(objectName);
        this.w = w;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    @Override
    public List<String> getDebugInfo() {
        List<String> info = super.getDebugInfo();
        info.add(String.format("world: %fx%f", w.width, w.height));
        return info;
    }

    public World getWorld() {
        return w;
    }
}
