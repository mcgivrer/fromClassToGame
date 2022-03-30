Feature: Loading a Level from its *.level file

  The `LevelLoader` service is dedicated to load objects from a level file and initialize all corresponding entities.

  Scenario: 01- loading a TileSet
    Given the LevelLoader is instantiated
    And the level file "test-level-tileset.lvl" is loaded
    Then a level named 'test-level-tileset' is defined
    And a TileSet with 3 Tiles is created
    And the Tile with code 'b' has a 'block' attribute
    And the Tile with code 'o' has a 'collectible' attribute
    And the Tile with code 'n' has a 'collectible' attribute