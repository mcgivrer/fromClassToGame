package fr.snapgames.fromclasstogame.demo.behaviors;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
import fr.snapgames.fromclasstogame.demo.entity.InventoryObject;

import java.awt.event.KeyEvent;

public class InventorySelectorBehavior implements Behavior<GameObject> {
    @Override
    public void onInput(GameObject go, ActionHandler ih) {
        InventoryObject io = (InventoryObject) go;
        testKey(ih, io, KeyEvent.VK_1, 0, 1);
        testKey(ih, io, KeyEvent.VK_2, 1, 2);
        testKey(ih, io, KeyEvent.VK_3, 2, 3);
        testKey(ih, io, KeyEvent.VK_4, 3, 4);
        testKey(ih, io, KeyEvent.VK_5, 4, 5);
        testKey(ih, io, KeyEvent.VK_6, 5, 6);
        testKey(ih, io, KeyEvent.VK_7, 6, 7);
        testKey(ih, io, KeyEvent.VK_8, 7, 8);
        testKey(ih, io, KeyEvent.VK_9, 8, 9);
        testKey(ih, io, KeyEvent.VK_0, 9, 10);
    }

    private void testKey(ActionHandler ih, InventoryObject io, int vk1, int i, int i2) {
        if (ih.get(vk1) && io.getNbPlaces() > i) {
            io.setSelectedIndex(i2);
        }
    }

    @Override
    public void onUpdate(GameObject go, long dt) {
        // this behavior has nothing on update side
    }

    @Override
    public void onRender(GameObject go, Render r) {
        // this behavior has nothing on render side
    }

    @Override
    public void onAction(GameObject go, ActionHandler.ACTIONS action) {
        // this behavior has nothing to do with action.
    }
}
