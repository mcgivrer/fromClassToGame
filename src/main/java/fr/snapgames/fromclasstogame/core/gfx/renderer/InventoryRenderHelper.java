package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.demo.entity.InventoryObject;

public class InventoryRenderHelper implements RenderHelper {
    private BufferedImage selector;
    private BufferedImage selected;
    private int spacing = 2;

    public InventoryRenderHelper() {
        try {
            selector = ResourceManager.getImage("images/tiles01.png:inventory_selector");
            selected = ResourceManager.getImage("images/tiles01.png:inventory_selected");
        } catch (UnknownResource e) {
            System.err.println("Unable to load resource: " + e.getMessage());
        }
    }


    @Override
    public String getType() {
        return InventoryObject.class.getName();
    }

    @Override
    public void draw(Graphics2D g, Object o) {
        InventoryObject go = (InventoryObject) o;

        // retrieve all object from the inventory
        List<GameObject> itemImages = go.getItems().stream().filter((v) -> v.getAttribute("inventory") != null).collect(Collectors.toList());

        // parse all available places and display corresponding object.
        for (int i = 0; i < go.getNbPlaces(); i++) {
            int rx = (int) go.position.x - (selector.getWidth() + spacing) * go.getNbPlaces();
            int ry = (int) go.position.y - (selector.getHeight() + spacing);
            BufferedImage sel = go.getSelectedIndex() == i + 1 ? selected : selector;
            g.drawImage(sel,
                    (int) rx + (i * (selector.getWidth()) + spacing),
                    (int) ry,
                    null);
            if (!itemImages.isEmpty() && itemImages.size() > i) {
                BufferedImage itemImg = (BufferedImage) itemImages.get(i).getAttribute("inventory");
                int dx = (selector.getWidth() - itemImg.getWidth()) / 2;
                int dy = (selector.getHeight() - itemImg.getHeight()) / 2;
                g.drawImage(itemImg,
                        (int) rx + (i * (selector.getWidth()) + spacing + 1 + dx),
                        (int) ry + 1 + dy, null);
            }
        }
    }
}
