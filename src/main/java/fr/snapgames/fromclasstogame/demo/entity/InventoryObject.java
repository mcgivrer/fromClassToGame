package fr.snapgames.fromclasstogame.demo.entity;

import java.util.ArrayList;
import java.util.List;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.PEType;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

/**
 * The InventoryObject will display a list of Object Items in the HUD dedicated area.
 */
public class InventoryObject extends GameObject {

    private int nbPlaces = 1;
    private int selectedIndex = 1;

    List<GameObject> items = new ArrayList<>();

    /**
     * Create a new {@link InventoryObject} with its name.
     *
     * @param name
     * @param position
     */
    public InventoryObject(String name, Vector2d position) {
        super(name, position);
        this.physicType = PEType.STATIC;
    }

    /**
     * Add an item into the Inventory. the object must have an `inventory`
     * attributes with a {@link java.awt.image.BufferedImage} to be displayed
     *
     * @param gio the GameObject to be added into the inventory item list.
     * @return the updated InventoryObject.
     */
    public InventoryObject add(GameObject gio) {
        if (!gio.getAttribute("inventory", "none").equals("none")) {
            items.add(gio);
        }
        return this;
    }

    /**
     * Remove an item from the item list.
     *
     * @param gio the GameObject to be removed from the Inventory item list.
     */
    public void remove(GameObject gio) {
        items.remove(gio);
    }

    /**
     * retrieve all the {@link InventoryObject} item list.
     *
     * @return A list of {@link GameObject}.
     */
    public List<GameObject> getItems() {
        return items;
    }

    /**
     * Set the list of items fo rthis {@link InventoryObject}
     *
     * @param items a list of GameObject (with a defined `inventory` attribute).
     */
    public void setItems(List<GameObject> items) {
        this.items = items;
    }

    /**
     * Define the number of place in the item list.
     *
     * @param i the number of items in the list
     * @return the {@link InventoryObject} updated.
     */
    public InventoryObject setNbPlace(int i) {
        this.nbPlaces = i;
        return this;
    }

    /**
     * retrieve the number of items displayed into the inventory.
     *
     * @return the number of items.
     */
    public int getNbPlaces() {
        return nbPlaces;
    }

    /**
     * return the current selected index in the list of inventory items.
     *
     * @return
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * select the highlighted Item.
     *
     * @param index the index of the item to be highlighted.
     * @return the updated InventoryObject.
     */
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
        box.update(this, new Vector2d(-nbPlaces * 16, 16));
        setDebugOffset(-nbPlaces * 16, 16);
    }
}
