package fr.snapgames.fromclasstogame.core.gfx;

import java.awt.Graphics2D;

import fr.snapgames.fromclasstogame.core.entity.GameObject;

public class GameObjectRenderHelper implements RenderHelper {

    @Override
    public void draw(Graphics2D g, Object o) {
        GameObject go = (GameObject) o;

        if (go.image != null) {
            g.drawImage(go.image, (int) (go.x), (int) (go.y), null);
        } else {
            g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
        }

    }

    @Override
    public String getType() {
        return GameObject.class.getName();
    }

}
