package fr.snapgames.fromclasstogame.demo.render;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.gfx.renderer.AbstractRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.demo.entity.InventoryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Draw InventoryObject
 */
public class InventoryRenderHelper extends AbstractRenderHelper implements RenderHelper<InventoryObject> {

    private static final Logger logger = LoggerFactory.getLogger(InventoryRenderHelper.class);

    private BufferedImage selector;
    private BufferedImage selected;
    private final int spacing = 2;

    /**
     * Prepare resources for Inventory rendering.
     *
     * @param r the Renderer to use this helper
     */
    public InventoryRenderHelper(Renderer r) {
        super(r);
        try {
            selector = ResourceManager.getImage("images/tiles01.png:inventory_selector");
            selected = ResourceManager.getImage("images/tiles01.png:inventory_selected");
        } catch (UnknownResource e) {
            logger.error("Unable to load resource: " + e.getMessage());
        }
    }


    @Override
    public String getType() {
        return InventoryObject.class.getName();
    }

    /**
     * Draw all inventory placeholder and object if filled.
     *
     * @param g               the Graphics2D interface to render GameObject
     * @param inventoryObject the GameObject to be drawn by this helper.
     */
    @Override
    public void draw(Graphics2D g, InventoryObject inventoryObject) {

        // retrieve all object from the inventory
        List<GameObject> itemImages = inventoryObject.getItems().stream().filter((v) -> !v.getAttribute("inventory", "none").equals("none")).collect(Collectors.toList());
        int iw = (selector.getWidth() + spacing) * inventoryObject.getNbPlaces();
        int ih = (selector.getHeight() + spacing);
        inventoryObject.width = iw;
        inventoryObject.height = ih;
        // parse all available places and display corresponding object.
        for (int i = 0; i < inventoryObject.getNbPlaces(); i++) {
            int rx = (int) inventoryObject.position.x - (selector.getWidth() + spacing) * inventoryObject.getNbPlaces();
            int ry = (int) inventoryObject.position.y - (selector.getHeight() + spacing);
            BufferedImage sel = inventoryObject.getSelectedIndex() == i + 1 ? selected : selector;
            g.drawImage(sel,
                    rx + (i * (selector.getWidth()) + spacing),
                    ry,
                    null);
            if (!itemImages.isEmpty() && itemImages.size() > i) {
                BufferedImage itemImg = (BufferedImage) itemImages.get(i).getAttribute("inventory", new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
                int dx = (selector.getWidth() - itemImg.getWidth()) / 2;
                int dy = (selector.getHeight() - itemImg.getHeight()) / 2;
                g.drawImage(itemImg,
                        rx + (i * (selector.getWidth()) + spacing + 1 + dx),
                        ry + 1 + dy,
                        null);
            }
        }
    }

    /**
     * Add specific inventory rendering
     *
     * @param g               the Graphics2D interface to render GameObject
     * @param inventoryObject the GameObject to be drawn by this helper.
     */
    @Override
    public void drawDebugInfo(Graphics2D g, InventoryObject inventoryObject) {
        super.drawDebugInfo(g, inventoryObject);
        if (inventoryObject.getDebug() > 2) {
            Color itemColorSelected = new Color(0.1f, 0.8f, 0.3f, 0.4f);
            Color itemColorEmpty = new Color(0.8f, 0.3f, 0.1f, 0.4f);
            List<GameObject> itemImages = inventoryObject.getItems().stream().filter((v) -> !v.getAttribute("inventory", "none").equals("none")).collect(Collectors.toList());
            int iw = (selector.getWidth() + spacing) * inventoryObject.getNbPlaces();
            int ih = (selector.getHeight() + spacing);
            inventoryObject.width = iw;
            inventoryObject.height = ih;
            // parse all available places and display corresponding object.
            for (int i = 0; i < inventoryObject.getNbPlaces(); i++) {
                int rx = (int) inventoryObject.position.x - (selector.getWidth() + spacing) * inventoryObject.getNbPlaces();
                int ry = (int) inventoryObject.position.y - (selector.getHeight() + spacing);
                if (!itemImages.isEmpty() && itemImages.size() > i) {
                    g.setColor(itemColorSelected);
                } else {
                    g.setColor(itemColorEmpty);
                }
                g.fillRect(
                        (int) rx + (i * (selector.getWidth()) + spacing + 1),
                        (int) ry + 1,
                        selector.getWidth() - 2, selector.getHeight() - 2);
                if (i == inventoryObject.getSelectedIndex()) {
                    g.setColor(new Color(0.9f, 0.9f, 0.0f, 0.5f));
                    g.drawRect(
                            (int) rx + (i * (selector.getWidth()) + spacing + 1),
                            (int) ry + 1,
                            selector.getWidth() - 2, selector.getHeight() - 2);

                }

            }
        }
    }
}
