package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.physic.PEType;
import fr.snapgames.fromclasstogame.core.physic.World;

import java.util.List;

/**
 * Define a Debug viewport grid visualizer for debug purpose only.
 *
 * @author Frédéric Delorme
 * @since 0.0.3
 */
public class DebugViewportGrid extends GameObject {
    public int gridX;
    public int gridY;
    private World world;


    /**
     * Create a DebugViewport object to display invisible information on debug mode.
     *
     * @param objectName
     */
    public DebugViewportGrid(String objectName) {
        super(objectName);
        setGridSize(16, 16);
        this.setDebug(1);
        setPhysicType(PEType.STATIC);
    }

    public DebugViewportGrid(String objectName, World w, int gridX, int gridY) {
        this(objectName);
        setWorld(w);
        setGridSize(gridX, gridY);
    }

    @Override
    public List<String> getDebugInfo() {
        List<String> info = super.getDebugInfo();
        info.add(String.format("world: %fx%f", world.width, world.height));
        return info;
    }

    public DebugViewportGrid setWorld(World w) {
        this.world = w;
        return this;

    }

    public DebugViewportGrid setGridSize(int width, int height) {
        this.gridX = width;
        this.gridY = height;
        return this;
    }

    public World getWorld() {
        return world;
    }
}
