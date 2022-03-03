package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.system.System;

import java.util.HashMap;
import java.util.Map;

/**
 * The pool manager maintains a list of EntityPool to store GameObject.
 *
 * @author Frédéric Delorme
 * @since 0.0.2
 */
public class EntityPoolManager extends System {

    /**
     * Map of Pool
     */
    private Map<String, EntityPool> entityPools = new HashMap<>();

    /**
     * Build a pool manager
     *
     * @param g
     */
    public EntityPoolManager(Game g) {
        super(g);
    }

    /**
     * Add a Pool of Entity.
     *
     * @param ep
     */
    public void addPool(EntityPool ep) {
        this.entityPools.put(ep.getName(), ep);
    }

    /**
     * Retrieve the EntityPool named entityPoolName.
     *
     * @param entityPoolName the name of the entity pool to be retrieved.
     * @return the corresponding entity pool.
     */
    public EntityPool get(String entityPoolName) {
        assert (this.entityPools.containsKey(entityPoolName));
        return this.entityPools.get(entityPoolName);
    }

    /**
     * Retrieve the name of the service.
     *
     * @return the name of this System.
     */
    public void removePool(String entityPoolName) {
        this.entityPools.remove(entityPoolName);
    }

    /**
     * Create an EntityPool
     *
     * @param name the name for this new EntityPool.
     */
    public void createPool(String name) {
        addPool(new EntityPool(name, 1000));
    }

    /**
     * Retrieve the EntityPool on its name.
     *
     * @param name the name of the EntityPool to retrieve.
     * @return The corresponding EntityPool.
     */
    public EntityPool getPool(String name) {
        return entityPools.get(name);
    }


    @Override
    public String getName() {
        return EntityPoolManager.class.getName();
    }

    @Override
    public int initialize(Configuration config) {
        return 1;
    }

    @Override
    public void dispose() {
        entityPools.clear();
    }

}
