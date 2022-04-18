package fr.snapgames.fromclasstogame.core.entity;

public interface Entity<T> {

    boolean isActive();

    T setActive(boolean active);
}