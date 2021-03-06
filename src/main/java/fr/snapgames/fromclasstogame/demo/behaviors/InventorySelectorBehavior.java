package fr.snapgames.fromclasstogame.demo.behaviors;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.demo.entity.InventoryObject;

import java.awt.event.KeyEvent;

public class InventorySelectorBehavior implements Behavior<GameObject> {
    @Override
    public void onInput(GameObject entity, ActionHandler ah) {
        InventoryObject io = (InventoryObject) entity;
        testKey(ah, io, KeyEvent.VK_1, 1);
        testKey(ah, io, KeyEvent.VK_2, 2);
        testKey(ah, io, KeyEvent.VK_3, 3);
        testKey(ah, io, KeyEvent.VK_4, 4);
        testKey(ah, io, KeyEvent.VK_5, 5);
        testKey(ah, io, KeyEvent.VK_6, 6);
        testKey(ah, io, KeyEvent.VK_7, 7);
        testKey(ah, io, KeyEvent.VK_8, 8);
        testKey(ah, io, KeyEvent.VK_9, 9);
        testKey(ah, io, KeyEvent.VK_0, 10);
    }

    private void testKey(ActionHandler ih, InventoryObject io, int vk1, int placeHolderIndex) {
        if (ih.get(vk1) && io.getNbPlaces() > placeHolderIndex - 1) {
            io.setSelectedIndex(placeHolderIndex);
        }
    }

}
