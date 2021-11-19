package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;

public class CopyObjectPosition implements Behavior {
    GameObject target;

    public CopyObjectPosition(GameObject target) {
        this.target = target;
    }

    @Override
    public void onInput(Object go, ActionHandler ih) {

    }

    @Override
    public void onUpdate(Object go, long dt) {
        GameObject cO = (GameObject) go;
        cO.setPosition(target.position);
    }

    @Override
    public void onRender(Object go, Render r) {

    }

    @Override
    public void onAction(Object go, ActionHandler.ACTIONS action) {

    }
}
