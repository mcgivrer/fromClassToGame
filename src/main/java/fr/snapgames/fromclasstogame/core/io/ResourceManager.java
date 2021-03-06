package fr.snapgames.fromclasstogame.core.io;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The ResourceManager service to cache Images and Fonts.
 *
 * @author Frédéric Delorme
 * @since 0.0.1
 */
public class ResourceManager {

    private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    /**
     * Internal Resource cache.
     */
    private static final Map<String, Object> resources = new HashMap<>();
    /**
     * parent Game.
     */
    private static Game game;

    /**
     * Create the ResourceManager.
     */
    private ResourceManager() {
    }

    /**
     * Initialize the ResourceManager
     *
     * @param g
     */
    public static void initialize(Game g) {
        game = g;
    }

    private static Font readFont(String path) {
        Font font = null;
        Optional<InputStream> ois;
        try {
            ois = Optional.ofNullable(ResourceManager.class.getResourceAsStream(path));
            if (ois.isEmpty()) {
                ois = Optional.ofNullable(ResourceManager.class.getProtectionDomain().getClassLoader().getResourceAsStream(path));
            }
            if (ois.isEmpty()) {
                ois = Optional.ofNullable(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
            }
            if (ois.isPresent()) {
                font = Font.createFont(Font.TRUETYPE_FONT, ois.get());
            }
        } catch (FontFormatException | IOException e) {
            logger.error("Unable to read font {}, use default one", path, e);
        } finally {
            if (font == null) {
                font = game.getRenderer().getGraphics().getFont();
            }
        }
        return font;
    }

    private static BufferedImage readImage(String path) throws UnknownResource {
        BufferedImage image = null;
        try {
            InputStream is = ResourceManager.class.getClassLoader().getResourceAsStream(path);
            if (is == null) {
                throw new UnknownResource(path, null);
            }
            image = ImageIO.read(is);
        } catch (IOException e) {
            logger.error("Unable to read image {}", path, e);
            throw new UnknownResource(path, e);
        }
        return image;
    }

    private static BufferedImage readImage(String path, int x, int y, int w, int h) {
        BufferedImage img = null;
        try {
            img = readImage(path);
            if (img != null) {
                return img.getSubimage(x, y, w, h);
            }
        } catch (NullPointerException | UnknownResource npe) {
            logger.error("Unable to slice image {}:{},{},{},{}", path, x, y, w, h, npe);
        }
        return img;
    }

    public static Font getFont(String fontPath) {
        Font f = null;
        if (resources.containsKey(fontPath)) {
            f = (Font) resources.get(fontPath);
        } else {
            f = readFont(fontPath);
            if (f != null) {
                resources.put(fontPath, f);
            }
        }
        return f;
    }

    public static BufferedImage getImage(String imagePath) throws UnknownResource {
        if (resources.containsKey(imagePath)) {
            return (BufferedImage) resources.get(imagePath);
        } else {
            BufferedImage i = readImage(imagePath);
            if (i != null) {
                resources.put(imagePath, i);
            }
            return (BufferedImage) resources.get(imagePath);
        }
    }

    public static BufferedImage getSlicedImage(String imagePath, String internalName, int x, int y, int w, int h) {
        if (resources.containsKey(imagePath + ":" + internalName)) {
            return (BufferedImage) resources.get(imagePath + ":" + internalName);
        } else {
            BufferedImage i = readImage(imagePath, x, y, w, h);
            if (i != null) {
                resources.put(imagePath + ":" + internalName, i);
            }
            return (BufferedImage) resources.get(imagePath + ":" + internalName);
        }
    }

    public static Collection<Object> getResources() {
        return resources.values();
    }

    /**
     * free all cached resources
     */
    public static void dispose() {
        resources.clear();
    }
}
