package fr.snapgames.fromclasstogame.devtools;

import fr.snapgames.fromclasstogame.core.config.AbstractConfiguration;
import fr.snapgames.fromclasstogame.core.config.cli.IntegerArgParser;
import fr.snapgames.fromclasstogame.core.config.cli.StringArgParser;

import java.awt.*;

public class TileGeneratorConfig extends AbstractConfiguration {


    public int cols = 20, rows = 20, tileW = 16, tileH = 16;
    public String fileName = "raw-tile-template.png";
    public Color backgroundColor = Color.BLACK, textColor = Color.LIGHT_GRAY, lineColor = Color.DARK_GRAY;

    public TileGeneratorConfig(String configurationPath) {
        super(configurationPath);
        readValuesFromFile();
    }

    @Override
    public void initializeArgParser(String configurationPath) {
        cm.add(new IntegerArgParser("tileW", "tw", "tileW",
                "set the value for tile width ( default 16 pixels)",
                "tilegen.tile.width", 16));
        cm.add(new IntegerArgParser("tileH", "th", "tileH",
                "set the value for tile height ( default 16 pixels)",
                "tilegen.tile.height", 16));
        cm.add(new IntegerArgParser("cols", "c", "cols",
                "set the value for tileset columns (default 20)",
                "tilegen.tileset.cols", 20));
        cm.add(new IntegerArgParser("rows", "r",
                "rows",
                "set the value for tileset rows (default 20)",
                "tilegen.tileset.rows", 20));
        cm.add(new StringArgParser("filename", "the Filename for the generated image",
                "f", "filename",
                "tilegen.output.filename", "raw-tileset-template.png"));
        cm.add(new ColorArgParser("backgroundColor", "Background color for the generated image",
                "b",
                "backcolor",
                "tilegen.color.background", Color.BLACK));
        cm.add(new ColorArgParser("textColor", "Text color for the generated image",
                "t",
                "textcolor",
                "tilegen.color.text", Color.LIGHT_GRAY));
        cm.add(new ColorArgParser("lineColor", "Rectangle lines color for the generated image",
                "l",
                "linecolor",
                "tilegen.color.lines", Color.DARK_GRAY));
    }

    @Override
    public void getValuesFromCM() {
        tileH = (Integer) cm.getValue("tileH");
        tileW = (Integer) cm.getValue("tileW");
        cols = (Integer) cm.getValue("cols");
        rows = (Integer) cm.getValue("rows");
        fileName = (String) cm.getValue("filename");
        backgroundColor = (Color) cm.getValue("backgroundColor");
        lineColor = (Color) cm.getValue("lineColor");
        textColor = (Color) cm.getValue("textColor");
    }
}
