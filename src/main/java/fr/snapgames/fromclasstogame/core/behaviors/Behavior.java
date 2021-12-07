package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;

public interface Behavior<T> {

    default void onCreate(T go) {
        // Nothing specific to do by default.
    }

    default void onInput(T go, ActionHandler ih) {
        // Nothing specific to do by default.

    }

    default void onUpdate(T go, long dt) {
        // Nothing specific to do by default.

    }

    default void onRender(T go, Render r) {
        // Nothing specific to do by default.

    }

    default void onAction(T go, Integer action) {
        // Nothing specific to do by default.

    }

}
