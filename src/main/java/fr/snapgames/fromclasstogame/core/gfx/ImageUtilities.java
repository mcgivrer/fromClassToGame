package fr.snapgames.fromclasstogame.core.gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtilities {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtilities.class);

    public static void save(String filename, BufferedImage img) {
        try {
            File out = new File(filename);
            ImageIO.write(img, "PNG", out);

            logger.info("Write screenshot to {}", filename);
        } catch (IOException e) {
            logger.error("Unable to write screenshot to {}:{}", filename, e.getMessage());
        }
    }
}
