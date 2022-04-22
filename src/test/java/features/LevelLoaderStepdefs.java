package features;

import fr.snapgames.fromclasstogame.core.entity.tilemap.TileMap;
import fr.snapgames.fromclasstogame.core.entity.tilemap.TileSet;
import fr.snapgames.fromclasstogame.core.io.LevelLoader;

import io.cucumber.java8.En;

import static org.junit.Assert.*;

/**
 * The Step definition for the LevelLoader and Level.
 */
public class LevelLoaderStepdefs implements En {

    LevelLoader ll;
    TileMap level;

    public LevelLoaderStepdefs() {
        Given("the LevelLoader is instantiated", () -> {
            ll = new LevelLoader(null);
        });
        And("the level file {string} is loaded", (String levelFileName) -> {
            level = ll.loadFrom(levelFileName);
        });
        Then("a level named {string} is defined", (String levelName) -> {
            assertEquals("The Level has not the right name", levelName, level.name);
        });
        And("a TileSet with {int} Tiles is created", (Integer nbTiles) -> {
            TileSet ts = level.getTileSets().get(0);
            assertEquals("The Tiles has not been initialized with " + nbTiles + " tiles !", nbTiles.intValue(), ts.getTiles().size());
        });
        And("the Tile with code {string} has a {string} attribute", (String tileCode, String tileAttributeName) -> {
            TileSet ts = level.getTileSets().get(0);
            assertTrue(ts.getTiles().get(tileCode).isAttributeExist(tileAttributeName));
        });
        And("the Tile with code {string} has a {string} attribute equals to {string}", (String tileCode, String attrName, String attrValue) -> {
            TileSet ts = level.getTileSets().get(0);
            assertEquals(attrValue, ts.getTiles().get(tileCode).getAttribute(attrName));

        });
        And("the Tile with code {string} has a {string} attribute equals to {int}", (String tileCode, String attrName, Integer attrValue) -> {
            TileSet ts = level.getTileSets().get(0);
            assertEquals(attrValue, ts.getTiles().get(tileCode).getAttribute(attrName));
        });
        And("the TileMap has an id set to {string}", (String idValue) -> {
            assertEquals(idValue, level.getAttribute("id", null));
        });
        And("the TileMap has an world set to {int}", (Integer worldAttrValue) -> {
            assertEquals(worldAttrValue, level.getAttribute("world", null));
        });
        And("the TileMap has an level set to {int}", (Integer levelAttrValue) -> {
            assertEquals(levelAttrValue, level.getAttribute("level", null));
        });
        And("the TileMap has a name set to {string}", (String nameValue) -> {
            assertEquals(nameValue, level.name);
        });

        And("the TileMap has a title set to {string}", (String titleAttrValue) -> {
            assertEquals(titleAttrValue, level.getAttribute("title", null));
        });
        And("the TileMap has a description set to {string}", (String descriptionAttrValue) -> {
            assertEquals(descriptionAttrValue, level.getAttribute("description", null));
        });
        And("a GameObject {string} has been created", (String gameObjectName) -> {
            assertNotNull(level.getGameObject(gameObjectName));
        });

    }
}
