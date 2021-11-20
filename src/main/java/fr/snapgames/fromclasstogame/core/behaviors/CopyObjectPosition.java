package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Utils;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class CopyObjectPosition implements Behavior {
    GameObject target;
    Vector2d offset;

    public CopyObjectPosition(GameObject target) {
        this.target = target;
    }

    public CopyObjectPosition(GameObject target, Vector2d offset) {
        this.target = target;
        this.offset = offset;
    }

    @Override
    public void onInput(Object go, ActionHandler ih) {

    }

    @Override
    public void onUpdate(Object go, long dt) {
        GameObject cO = (GameObject) go;
        cO.setPosition(Utils.add(target.position, this.offset));
    }

    @Override
    public void onRender(Object go, Render r) {

    }

    @Override
    public void onAction(Object go, ActionHandler.ACTIONS action) {

    }
}
