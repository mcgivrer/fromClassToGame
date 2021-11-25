package fr.snapgames.fromclasstogame.demo.render;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.renderer.AbstractRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.demo.entity.InventoryObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryRenderHelper extends AbstractRenderHelper implements RenderHelper<InventoryObject> {
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
    public void draw(Graphics2D g, InventoryObject o) {
        InventoryObject go = (InventoryObject) o;

        // retrieve all object from the inventory
        List<GameObject> itemImages = go.getItems().stream().filter((v) -> !v.getAttribute("inventory", "none").equals("none")).collect(Collectors.toList());

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
                BufferedImage itemImg = (BufferedImage) itemImages.get(i).getAttribute("inventory", new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
                int dx = (selector.getWidth() - itemImg.getWidth()) / 2;
                int dy = (selector.getHeight() - itemImg.getHeight()) / 2;
                g.drawImage(itemImg,
                        (int) rx + (i * (selector.getWidth()) + spacing + 1 + dx),
                        (int) ry + 1 + dy, null);
            }
        }
    }

    @Override
    public void drawDebugInfo(Graphics2D g, InventoryObject go) {
        super.drawDebugInfo(g, go);
    }
}
