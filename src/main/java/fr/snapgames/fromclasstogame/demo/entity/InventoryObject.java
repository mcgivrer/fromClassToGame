package fr.snapgames.fromclasstogame.demo.entity;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class InventoryObject extends GameObject {

    List<GameObject> items = new ArrayList<>();
    private int nbPlaces = 1;
    private int selectedIndex = 1;

    @Deprecated
    public InventoryObject(String name, double x, double y) {
        this(name, new Vector2d(x, y));


    }

    public InventoryObject(String name, Vector2d position) {
        super(name, position);
    }

    public InventoryObject addItem(GameObject gio) {
        if (!gio.getAttribute("inventory", "none").equals("none")) {
            items.add(gio);
        }
        return this;
    }

    public void remove(GameObject gio) {
        if (items.contains(gio)) {
            items.remove(gio);
        }
    }

    public List<GameObject> getItems() {
        return items;
    }

    public void setItems(List<GameObject> items) {
        this.items = items;
    }

    public InventoryObject setNbPlace(int i) {
        this.nbPlaces = i;
        return this;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public InventoryObject setSelectedIndex(int index) {
        this.selectedIndex = index;
        return this;
    }

    @Override
    public List<String> getDebugInfo() {

        debugOffsetX = -80;
        debugOffsetY = -20;
        List<String> dbgInfo = super.getDebugInfo();
        dbgInfo.add(String.format("size: %d", this.items.size()));
        return dbgInfo;
    }
}
