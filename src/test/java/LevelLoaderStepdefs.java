import fr.snapgames.fromclasstogame.core.levels.Level;
import fr.snapgames.fromclasstogame.core.levels.LevelLoader;
import io.cucumber.java8.En;

import static org.junit.Assert.assertEquals;

/**
 * The Step definition for the LevelLoader and Level.
 */
public class LevelLoaderStepdefs implements En {

    LevelLoader ll;
    Level level;

    public LevelLoaderStepdefs() {
        Given("the LevelLoader is instantiated", () -> {
            ll = new LevelLoader();
        });
        And("the level file {string} is loaded", (String levelFileName) -> {
            level = ll.loadFrom(levelFileName);
        });
        Then("a level named {string} is defined", (String levelName) -> {
            assertEquals("The Level has not the right name", levelName, level.getName());
        });
        And("a TileSet with {int} Tiles is created", (Integer nbTiles) -> {

        });


    }
}
