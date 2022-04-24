package fr.snapgames.fromclasstogame.core.entity;

/**
 * Exception thrown when an unknown object is managed.
 */
public class UnknownGameObject extends Exception {
    public UnknownGameObject(String name) {
        super(name);
    }
}
