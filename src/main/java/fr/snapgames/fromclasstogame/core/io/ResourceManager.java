package fr.snapgames.fromclasstogame.core.io;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.audio.SoundClip;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.exceptions.io.UnknownResource;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.*;

/**
 * The ResourceManager service to cache Images and Fonts.
 *
 * @author Frédéric Delorme
 * @since 0.0.1
 */
public class ResourceManager extends System {

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
    public ResourceManager(Game g) {
        super(g);
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

    public static void disposeAll() {
        resources.clear();
    }

    public static SoundClip getSoundClip(String filename) {

        InputStream stream = ResourceManager.class.getResourceAsStream(filename);
        SoundClip sc = new SoundClip(filename, stream);
        if (sc != null) {
            resources.put(filename, sc);
        }
        logger.debug("'{}' added as an audio resource", filename);
        return sc;
    }

    public static java.util.List<String> getFile(String attributesFilename) {
        if (resources.containsKey(attributesFilename)) {
            return (List<String>) resources.get(attributesFilename);
        } else {
            List<String> i = readTextFile(attributesFilename);
            if (i != null) {
                resources.put(attributesFilename, i);
            }
            return (List<String>) resources.get(attributesFilename);
        }
    }

    private static List<String> readTextFile(String filename) {
        List<String> values = new ArrayList<>();

        try {
            InputStream is = ResourceManager.class.getResourceAsStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                values.add(line);
            }
        } catch (IOException ioe) {
            logger.error("Unable to read file " + filename, ioe);

        }


        return values;
    }

    /**
     * Clear all resources from resource manager.
     */
    public static void clear() {
        resources.clear();
    }

    /**
     * free all cached resources
     */
    public void dispose() {
        resources.clear();
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int initialize(Configuration config) {
        clear();
        return 0;
    }
}
