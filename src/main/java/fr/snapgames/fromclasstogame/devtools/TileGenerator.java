package fr.snapgames.fromclasstogame.devtools;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import fr.snapgames.fromclasstogame.core.config.cli.exception.ArgumentUnknownException;
import fr.snapgames.fromclasstogame.core.gfx.ImageUtilities;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;

/**
 * This class is a Tile template generator.
 * <p>
 * It creates an image of size corresponding to:
 * <pre><code>
 *     width = (tileW x cols)
 *     height= (tileH x rows)
 * </code></pre>
 * <p>
 * with a final matrix draw of rectangles of tileW x tileH with  an ind stating at 0 to the max number of possible tiles.
 * <p>
 * <blockquote><strong>NOTE:</strong> the file is output to %USERPROFILE% path by default.</blockquote>
 * <p>
 * the command line options can be got through:
 * <pre>
 * c:\> java -cp fromclasstogame-{version}-shaded.jar fr.snapgames.fromclasstogame.devtools.TileGenerator -Dh
 * </pre>
 *
 * @author Frédéric Delorme
 * @see ResourceManager#readFont(String)
 * @since 0.0.2
 */
public class TileGenerator {


    private String fileName = "raw-tile-template.png";
    private int screenShotIndex = 0;
    private int cols = 20, rows = 20, tileW = 16, tileH = 16;
    private Color backgroundColor = Color.BLACK;
    private Color lineColor = Color.GRAY;
    private Color textColor = Color.LIGHT_GRAY;

    public TileGenerator(String fileName) {
        this.fileName = fileName;
    }

    private void saveImage(BufferedImage img, String fileName) {
        ImageUtilities.save(fileName, img);
    }

    private void generateTiles(BufferedImage img, int tileW, int tileH) {
        Font f = ResourceManager.readFont("fonts/FreePixel.ttf").deriveFont(8.0f);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // clear image area
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        // draw all tiles
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();

        int id = 0;
        for (int y = 0; y < img.getHeight(); y += tileH) {
            for (int x = 0; x < img.getWidth(); x += tileW) {
                g.setColor(this.lineColor);
                g.drawRect(x, y, tileW, tileH);
                g.setColor(this.textColor);
                g.drawString(String.format("%03d", id), x + 2, y + 12);
                id++;
            }
        }
    }

    private TileGenerator build() {
        BufferedImage img = new BufferedImage(this.cols * this.tileW, this.rows * this.tileH, BufferedImage.TYPE_INT_ARGB);
        generateTiles(img, this.tileW, this.tileH);
        saveImage(img, this.fileName);
        return this;
    }

    private TileGenerator setTile(int tileW, int tileH) {
        this.tileW = tileW;
        this.tileH = tileH;
        return this;
    }


    private TileGenerator setBackgroundColor(Color bckColor) {
        this.backgroundColor = bckColor;
        return this;
    }

    private TileGenerator setTextColor(Color txtColor) {
        this.textColor = txtColor;
        return this;
    }

    private TileGenerator setLineColor(Color linColor) {
        this.lineColor = linColor;
        return this;
    }

    private TileGenerator setTilesetSize(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        return this;
    }


    public static void main(String[] args) {
        String user = System.getenv("USERPROFILE");
        TileGeneratorConfig config = new TileGeneratorConfig("tilegenerator");
        config.readValuesFromFile();
        try {
            config.parseArgs(args);
            config.getValuesFromCM();
            TileGenerator tg = new TileGenerator(user + "\\" + config.fileName)
                    .setTile(config.tileW, config.tileH)
                    .setTilesetSize(config.cols, config.rows)
                    .setBackgroundColor(config.backgroundColor)
                    .setTextColor(config.textColor)
                    .setLineColor(config.lineColor)
                    .build();
        } catch (ArgumentUnknownException e) {
            e.printStackTrace();
        }
    }
}
