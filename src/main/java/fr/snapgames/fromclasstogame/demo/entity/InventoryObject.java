package fr.snapgames.fromclasstogame.demo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

public class InventoryObject extends GameObject {

    private int nbPlaces = 1;
    private int selectedIndex = 1;

    List<GameObject> items = new ArrayList<>();

    @Deprecated
    public InventoryObject(String name, double x, double y) {
        super(name, new Vector2d(x, y));

    }

    public InventoryObject(String name, Vector2d position) {
        super(name, position);

    }

    public InventoryObject add(GameObject gio) {
        if (!gio.getAttribute("inventory", "none").equals("none")) {
            items.add(gio);
        }
        return this;
    }

    public void remove(GameObject gio) {
        items.remove(gio);
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
        List<String> dbgInfo = super.getDebugInfo();
        dbgInfo.add(String.format("size: %d", this.items.size()));
        return dbgInfo;
    }


    @Override
    public void update(long dt) {
        super.update(dt);
        bbox.update(this, new Vector2d(-nbPlaces * 16, 16));
        setDebugOffset(-nbPlaces * 16, 16);
    }
}
