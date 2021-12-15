package fr.snapgames.fromclasstogame.demo.render;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.gfx.Render;
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
    private int spacing = 2;

    /**
     * Prepare resources for Inventory rendering.
     *
     * @param r
     */
    public InventoryRenderHelper(Render r) {
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
     * @param g
     * @param o
     */
    @Override
    public void draw(Graphics2D g, InventoryObject o) {
        InventoryObject go = (InventoryObject) o;

        // retrieve all object from the inventory
        List<GameObject> itemImages = go.getItems().stream().filter((v) -> !v.getAttribute("inventory", "none").equals("none")).collect(Collectors.toList());
        int iw = (selector.getWidth() + spacing) * go.getNbPlaces();
        int ih = (selector.getHeight() + spacing);
        go.width = iw;
        go.height = ih;
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

    /**
     * Add specific inventory rendering
     *
     * @param g
     * @param go
     */
    @Override
    public void drawDebugInfo(Graphics2D g, InventoryObject go) {
        super.drawDebugInfo(g, go);
        if (go.getDebug() > 2) {
            Color itemColorSelected = new Color(0.1f, 0.8f, 0.3f, 0.4f);
            Color itemColorEmpty = new Color(0.8f, 0.3f, 0.1f, 0.4f);
            List<GameObject> itemImages = go.getItems().stream().filter((v) -> !v.getAttribute("inventory", "none").equals("none")).collect(Collectors.toList());
            int iw = (selector.getWidth() + spacing) * go.getNbPlaces();
            int ih = (selector.getHeight() + spacing);
            go.width = iw;
            go.height = ih;
            // parse all available places and display corresponding object.
            for (int i = 0; i < go.getNbPlaces(); i++) {
                int rx = (int) go.position.x - (selector.getWidth() + spacing) * go.getNbPlaces();
                int ry = (int) go.position.y - (selector.getHeight() + spacing);
                if (!itemImages.isEmpty() && itemImages.size() > i) {
                    g.setColor(itemColorSelected);
                } else {
                    g.setColor(itemColorEmpty);
                }
                g.fillRect(
                        (int) rx + (i * (selector.getWidth()) + spacing ),
                        (int) ry,
                        selector.getWidth() , selector.getHeight() );
                if (i == go.getSelectedIndex()-1) {
                    g.setColor(new Color(0.9f, 0.9f, 0.0f, 0.5f));
                    g.drawRect(
                            (int) rx + (i * (selector.getWidth()) + spacing),
                            (int) ry ,
                            selector.getWidth(), selector.getHeight());

                }

            }
        }
    }
}
