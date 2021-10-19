package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.InputHandler;

public interface Behavior {
    void input(GameObject go, InputHandler ih);

    void update(GameObject go, long dt);

    void render(GameObject go, Render r);
}
