package fr.snapgames.fromclasstogame.demo.entity;

import fr.snapgames.fromclasstogame.core.entity.GameObject;

import java.util.ArrayList;
import java.util.List;

public class InventoryObject extends GameObject {

    private int nbPlaces = 1;
    private int selectedIndex = 1;

    List<GameObject> items = new ArrayList<>();

    public InventoryObject(String name, double x, double y) {
        super(name, x, y);

    }

    public InventoryObject add(GameObject gio) {
        if (gio.getAttribute("inventory") != null) {
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
}
