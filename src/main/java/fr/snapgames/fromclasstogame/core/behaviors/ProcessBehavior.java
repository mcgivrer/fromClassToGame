package fr.snapgames.fromclasstogame.core.behaviors;

import java.util.List;

@FunctionalInterface
public interface ProcessBehavior<T> {
    void process(List<T> objects);
}
