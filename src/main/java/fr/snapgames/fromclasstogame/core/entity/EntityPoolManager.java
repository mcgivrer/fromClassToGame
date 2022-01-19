package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.system.System;

import java.util.HashMap;
import java.util.Map;

public class EntityPoolManager extends System {

    private Map<String, EntityPool> entityPools = new HashMap<>();

    public EntityPoolManager(Game g) {
        super(g);
    }

    public void addPool(EntityPool ep) {
        this.entityPools.put(ep.getName(), ep);
    }

    public EntityPool get(String entityPoolName) {
        assert (this.entityPools.containsKey(entityPoolName));
        return this.entityPools.get(entityPoolName);
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

    public void removePool(String entityPoolName) {
        this.entityPools.remove(entityPoolName);
    }
}
