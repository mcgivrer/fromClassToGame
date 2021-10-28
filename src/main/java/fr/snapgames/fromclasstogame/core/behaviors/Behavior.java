package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;

public interface Behavior {
    void input(GameObject go, ActionHandler ih);

    void update(GameObject go, long dt);

    void render(GameObject go, Render r);

    void onAction(GameObject go, ActionHandler.ACTIONS action);

}
