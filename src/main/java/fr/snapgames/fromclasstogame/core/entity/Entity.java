package fr.snapgames.fromclasstogame.core.entity;

import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public interface Entity<T> {
    Vector2d getPosition();

    T setPosition(Vector2d pos);

    boolean isActive();

    T setActive(boolean active);
}