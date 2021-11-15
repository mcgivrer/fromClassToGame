package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;

public interface Behavior<T> {

    default void onCreate(T go) {
        // Nothing specific to do by default.
    }

    void onInput(T go, ActionHandler ih);

    void onUpdate(T go, long dt);

    void onRender(T go, Render r);

    void onAction(T go, ActionHandler.ACTIONS action);

}
